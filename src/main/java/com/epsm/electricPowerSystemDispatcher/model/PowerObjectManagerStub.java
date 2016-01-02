package com.epsm.electricPowerSystemDispatcher.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.epsm.electricPowerSystemDispatcher.service.OutgoingMessageService;
import com.epsm.electricPowerSystemModel.model.dispatch.ConsumerParametersStub;
import com.epsm.electricPowerSystemModel.model.dispatch.ConsumptionPermissionStub;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerObjectParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationParameters;

//It just a stub. More complex model see com.epsm.electricPowerSystemModel.model.*;
public class PowerObjectManagerStub{
	private Map<Long, PowerObjectParameters> storedParameters;
	private PowerStationGenerationScheduleCalculatorStub calculator;
	private Logger logger;
	
	public PowerObjectManagerStub() {
		storedParameters = new ConcurrentHashMap<Long, PowerObjectParameters>();
		calculator = new PowerStationGenerationScheduleCalculatorStub();
		logger = LoggerFactory.getLogger(PowerObjectManagerStub.class);
	}
	
	@Autowired
	private OutgoingMessageService service;
	
	//Just a stub. Power object parameters have no effect.
	public void rememberPowerObjectParameters(PowerObjectParameters parameters){
		long powerObjectId = parameters.getPowerObjectId();
		storedParameters.putIfAbsent(powerObjectId, parameters);
	}
	
	public void sendMessage(long powerObjectId){
		PowerObjectParameters parameters = storedParameters.get(powerObjectId);
		
		if(parameters == null){
			logger.warn("Request sending message to PowerObject with id {} that is null.", powerObjectId);
			return;
		}else if(parameters instanceof PowerStationParameters){
			sendMessageToPowerStation(powerObjectId);
			logger.info("Sent schedule to power station with id {}.", powerObjectId);
		}else if(parameters instanceof ConsumerParametersStub){
			sendMessageToConsumer(powerObjectId);
			logger.info("Sent schedule to power station with id {}.", powerObjectId);
		}
		//schedule
		//message
		//unknown

	}

	public void sendMessageToPowerStation(long powerStationId){
		PowerStationGenerationSchedule schedule = calculator.getSchedule(powerStationId);
		service.sendMessageToPowerStation(schedule);
		
		logger.info("Schedule sent to power station with id {}." + powerStationId);
	}
	
	private void sendMessageToConsumer(long consumerId) {
		ConsumptionPermissionStub permission = new ConsumptionPermissionStub(LocalDateTime.MIN);
		
	}
}
