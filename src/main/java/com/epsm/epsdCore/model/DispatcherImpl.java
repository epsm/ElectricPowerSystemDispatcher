package com.epsm.epsdCore.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epsm.epsmCore.model.dispatch.Dispatcher;
import com.epsm.epsmCore.model.dispatch.Parameters;
import com.epsm.epsmCore.model.dispatch.State;
import com.epsm.epsmCore.model.generalModel.RealTimeOperations;
import com.epsm.epsmCore.model.generation.PowerStationGenerationSchedule;

public class DispatcherImpl implements Dispatcher, RealTimeOperations{
	private PowerObjectManagerStub objectManager;
	private StateSaver saver;
	private ObjectsConnector connector;
	private PowerObjectsDateTimeSource dateTimeSource;
	private List<PowerStationGenerationSchedule> schedules;
	private LocalDateTime dateTimeInSimulation;
	private LocalDate sentDate;
	private Logger logger;

	public DispatcherImpl(PowerObjectManagerStub objectManager, StateSaver saver, 
			ObjectsConnector connector, PowerObjectsDateTimeSource dateTimeSource){
		
		logger = LoggerFactory.getLogger(DispatcherImpl.class);
		
		if(objectManager == null){
			String message = "DispatcherImpl constructor: objectManager can't be null.";
			logger.error(message);
			throw new IllegalArgumentException(message);
		}else if(saver == null){
			String message = "DispatcherImpl constructor: saver can't be null.";
			logger.error(message);
			throw new IllegalArgumentException(message);
		}else if(connector == null){
			String message = "DispatcherImpl constructor: connector can't be null.";
			logger.error(message);
			throw new IllegalArgumentException(message);
		}else if(dateTimeSource == null){
			String message = "DispatcherImpl constructor: dateTimeSource can't be null.";
			logger.error(message);
			throw new IllegalArgumentException(message);
		}
		
		this.objectManager = objectManager;
		this.saver = saver;
		this.connector = connector;
		this.dateTimeSource = dateTimeSource;
		sentDate = LocalDate.MIN;
	}

	@Override
	public boolean registerObject(Parameters parameters){
		if(parameters == null){
			logger.warn("Received null parameters.");
		}else if(tryToRecognizeAndRegisterObject(parameters)){
			dateTimeSource.updateObjectsDateTime(parameters);
			logger.info("Received: {} from power object#{}.",
					parameters.getClass().getSimpleName(), parameters.getPowerObjectId());
			return true;
		}else{
			logger.warn("Recived unknown parameters: {}.", parameters);
		}
		
		return false;
	}
	
	private boolean tryToRecognizeAndRegisterObject(Parameters parameters){
		return objectManager.registerObjectIfItTypeIsKnown(parameters);
	}

	@Override
	public void acceptState(State state){
		if(state == null){
			logger.warn("Received null state.");
		}else if(isObjectRegistered(state)){
			savePowerObjectState(state);
			dateTimeSource.updateObjectsDateTime(state);
			logger.info("Received: {} from power object#{}.",
					state.getClass().getSimpleName(), state.getPowerObjectId());
		}else{
			logger.info("Recived state from unregisterd object#{}.", state.getPowerObjectId());
		}
	}
	
	private boolean isObjectRegistered(State state){
		long powerObjectId = state.getPowerObjectId();
		return objectManager.isObjectRegistered(powerObjectId);
	}
	
	private void savePowerObjectState(State state){
		saver.savePowerObjectState(state);
	}

	@Override
	public void doRealTimeDependingOperations() {
		getDateTimeInSimulation();
		
		if(isItTimeToSendStationGenerationSchedules()){
			sendSchedulesForPowerStations();
			refreshSentSchedulesDate();
		}
	}
	
	private void getDateTimeInSimulation(){
		dateTimeInSimulation = dateTimeSource.getPowerObjectsDateTime();
	}
	
	private boolean isItTimeToSendStationGenerationSchedules(){
		return isDateAppropriate() && isTimeAppropriate();
	}
	
	private boolean isDateAppropriate(){
		LocalDate dateInSimulation = dateTimeInSimulation.toLocalDate();
		return sentDate.isBefore(dateInSimulation);
	}
	
	private boolean isTimeAppropriate(){
		LocalTime timeInSimulation = dateTimeInSimulation.toLocalTime();
		return timeInSimulation.isAfter(Constants.TIME_TO_SEND_SCHEDULES);
	}
	
	private void sendSchedulesForPowerStations(){
		getSchedules();
		sendSchedules();	
	}
	
	private void getSchedules(){
		schedules = objectManager.getPowerStationGenerationSchedules();
	}
	
	private void sendSchedules(){
		for(PowerStationGenerationSchedule generationSchedule: schedules){
			connector.sendCommand(generationSchedule);
			logger.info("Sent: schedule {}.", generationSchedule);
		}
	}
	
	private void refreshSentSchedulesDate(){
		sentDate = dateTimeInSimulation.toLocalDate();
	}
}