package com.epsm.electricPowerSystemDispatcher.model;

import java.util.concurrent.ConcurrentSkipListSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.DispatcherService;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

//It just a stub. It doesn't do any calculation as real dispatcher. More complex model see in EPSM model.
@Component
public class DispatcherStub{
	private PowerStationGenerationScheduleCalculatorStub calculator 
			= new PowerStationGenerationScheduleCalculatorStub();  
	private PowerStationGenerationSchedule schedule;
	private ConcurrentSkipListSet<Integer> powerStations = new ConcurrentSkipListSet<Integer>();
	private ConcurrentSkipListSet<Integer> consumers = new ConcurrentSkipListSet<Integer>();
	private Logger logger = LoggerFactory.getLogger(DispatcherStub.class);
	
	@Autowired
	private DispatcherService service;
	
	public void registerPowerStation(PowerStationParameters parameters){
		int powerStationNumber = parameters.getPowerStationNumber();
		
		addPowerStation(powerStationNumber);
		sendConfirmationToPowerStation(powerStationNumber);
		startSendingScheduleToPowerStation(powerStationNumber);		
		logger.info("Power station with number {} registered.", powerStationNumber);
	}
	
	private void addPowerStation(int powerStationNumber){
		powerStations.add(powerStationNumber);
	}
	
	private void sendConfirmationToPowerStation(int powerStationNumber){
		service.sendConfirmationToPowerStation(powerStationNumber);
	}
	
	private void startSendingScheduleToPowerStation(int powerStationNumber){
		ScheduleSendingTimer timer = new ScheduleSendingTimer(this, powerStationNumber);
		timer.turnOn();
	}
	
	public void registerConsumer(int consumerNumber){
		addConsumer(consumerNumber);
		sendConfirmationToConsumer(consumerNumber);
		logger.info("Consumer with number {} registered.", consumerNumber);
	}
	
	private void addConsumer(int consumerNumber){
		consumers.add(consumerNumber);
	}
	
	private void sendConfirmationToConsumer(int consumerNumber){
		service.sendConfirmationToConsumer(consumerNumber);
	}
	
	public void sendGenerationScheduleToPowerStation(int powerStationNumber){
		prepareSchedule(powerStationNumber);
		service.sendGenerationScheduleToPowerStation(powerStationNumber, schedule);
		logger.info("Schedule was sended to power station №{}.", powerStationNumber);
	}
	
	private void prepareSchedule(int powerStationNumber){
		schedule = calculator.getSchedule(powerStationNumber);
	}
	
	public void acceptPowerStationState(PowerStationState state){
		int powerStationNumber = state.getPowerStationNumber();
		
		if(isPowerStationRegistered(powerStationNumber)){
			service.savePowerStationState(state);
		}else{
			logger.info("Unregister power station №{} tried to send it's state", powerStationNumber);
		}
	}
	
	private boolean isPowerStationRegistered(int powerStationNumber){
		return powerStations.contains(powerStationNumber);
	}
	
	public void acceptConsumerState(SavedConsumerState state){
		int consumerNumber = state.getConsumerNumber();
		
		if(isConsumerRegistered(consumerNumber)){
			service.saveConsumerState(state);
		}else{
			logger.info("Unregister consumer №{} tried to send it's state", consumerNumber);
		}
	}

	private boolean isConsumerRegistered(int powerStationNumber){
		return consumers.contains(powerStationNumber);
	}
}
