package com.epsm.electricPowerSystemDispatcher.model;

import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epsm.electricPowerSystemModel.model.consumption.ConsumerParametersStub;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumptionPermissionStub;
import com.epsm.electricPowerSystemModel.model.dispatch.Parameters;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationParameters;

//It just a stub. More complex model see com.epsm.electricPowerSystemModel.model.*;
public class PowerObjectManagerStub{
	private Map<Long, Parameters> storedParameters;
	private PowerStationGenerationScheduleCalculatorStub calculator;
	private Dispatcher dispatcher;
	private TimeService timeService;
	private Logger logger;
	
	public PowerObjectManagerStub(TimeService timeService, Dispatcher dispatcher) {
		if(timeService == null){
			String message = "PowerObjectManagerStub constructor: timeService can't be null.";
			throw new IllegalArgumentException(message);
		}else if(dispatcher == null){
			String message = "PowerObjectManagerStub constructor: dispatcher can't be null.";
			throw new IllegalArgumentException(message);
		}
		
		this.timeService = timeService;
		this.dispatcher = dispatcher;
		storedParameters = new ConcurrentHashMap<Long, Parameters>();
		calculator = new PowerStationGenerationScheduleCalculatorStub(timeService);
		logger = LoggerFactory.getLogger(PowerObjectManagerStub.class);
	}
	
	public boolean rememberObjectIfItTypeIsKnown(Parameters parameters){
		long powerObjectId = parameters.getPowerObjectId();
		
		if(parameters instanceof PowerStationParameters){
			storedParameters.putIfAbsent(powerObjectId, parameters);
			return true;
		}else if(parameters instanceof ConsumerParametersStub){
			storedParameters.putIfAbsent(powerObjectId, parameters);
			return true;
		}

		return false;
	}
	
	public void sendMessage(long powerObjectId){
		Parameters parameters = storedParameters.get(powerObjectId);
		
		if(parameters == null){
			logger.warn("Requested sending message to null powerObject#{}.", powerObjectId);
		}else if(parameters instanceof PowerStationParameters){
			sendMessageToPowerStation(powerObjectId);
		}else{
			sendMessageToConsumer(powerObjectId);
		}
	}

	private void sendMessageToPowerStation(long powerStationId){
		PowerStationGenerationSchedule schedule = calculator.getSchedule(powerStationId);
		dispatcher.sendCommand(schedule);
		
		logger.info("Schedule sent to power station#{}." + powerStationId);
	}
	
	private void sendMessageToConsumer(long consumerId) {
		ConsumptionPermissionStub permission = new ConsumptionPermissionStub(
				consumerId, timeService.getCurrentTime(), LocalTime.MIN);
		
		dispatcher.sendCommand(permission);
	}
}
