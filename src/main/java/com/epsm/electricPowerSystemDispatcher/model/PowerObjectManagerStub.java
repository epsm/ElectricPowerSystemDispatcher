package com.epsm.electricPowerSystemDispatcher.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.epsm.electricPowerSystemDispatcher.service.OutgoingMessageService;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerObjectParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;

//It just a stub. More complex model see com.epsm.electricPowerSystemModel.model.*;
public class PowerObjectManagerStub{
	private Map<Long, PowerObjectParameters> storedParameters;
	private PowerStationGenerationScheduleCalculatorStub calculator;
	private Logger logger;
	
	public PowerObjectManagerStub() {
		storedParameters = new ConcurrentHashMap<Long, PowerObjectParameters>();
		logger = LoggerFactory.getLogger(PowerObjectManagerStub.class);
	}
	
	@Autowired
	private OutgoingMessageService service;
	
	//Just a stub. Power object parameters have no effect.
	public void rememberPowerObjectParameters(PowerObjectParameters parameters){
		long powerObjectId = parameters.getPowerObjectId();
		storedParameters.put(powerObjectId, parameters);
	}
	
	public void sendMessage(long powerObjectId){
		//schedule
		//message
		//unknown

	}
	
	public void sendMessageToPowerStation(long powerStationId){
		PowerStationGenerationSchedule schedule = calculator.getSchedule(powerStationId);
		service.sendMessageToPowerStation(schedule);
		
		logger.info("Schedule sent to power station with id {}." + powerStationId);
	}
}
