package com.epsm.epsdCore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epsm.epsmCore.model.consumption.ConsumerParametersStub;
import com.epsm.epsmCore.model.dispatch.Parameters;
import com.epsm.epsmCore.model.generalModel.TimeService;
import com.epsm.epsmCore.model.generation.PowerStationGenerationSchedule;
import com.epsm.epsmCore.model.generation.PowerStationParameters;

//It just a stub. More complex model see com.epsm.electricPowerSystemModel.model.*;
public class PowerObjectManagerStub{
	private Map<Long, Parameters> regesteredObjectsParameters;
	private PowerStationGenerationScheduleCalculatorStub calculator;
	private Logger logger;
	
	public PowerObjectManagerStub(TimeService timeService) {
		logger = LoggerFactory.getLogger(PowerObjectManagerStub.class);
		
		if(timeService == null){
			String message = "PowerObjectManagerStub constructor: timeService can't be null.";
			logger.debug("Constructor: timeService can't be null.");
			throw new IllegalArgumentException(message);
		}
		
		regesteredObjectsParameters = new ConcurrentHashMap<Long, Parameters>();
		calculator = new PowerStationGenerationScheduleCalculatorStub(timeService);
	}
	
	public boolean registerObjectIfItTypeIsKnown(Parameters parameters){
		if(parameters instanceof PowerStationParameters){
			registerPowerObject(parameters);
			return true;
		}else if(parameters instanceof ConsumerParametersStub){
			registerPowerObject(parameters);
			return true;
		}
		
		logger.warn("Got: wrong parameters type {}.", parameters);
		return false;
	}
	
	private void registerPowerObject(Parameters parameters){
		long powerObjectId = parameters.getPowerObjectId();
		regesteredObjectsParameters.putIfAbsent(powerObjectId, parameters);
		logger.debug("Registered power object with parameters {}.", parameters);
	}
	
	public List<PowerStationGenerationSchedule> getPowerStationGenerationSchedules(){
		List<Long> ids = filterPowerStationIds();
		
		return getSchedulesForPowerStations(ids);
	}
	
	private List<Long> filterPowerStationIds(){
		ArrayList<Long> ids = new ArrayList<Long>(); 
		
		for(Parameters parameters: regesteredObjectsParameters.values()){
			if(parameters instanceof PowerStationParameters){
				ids.add(parameters.getPowerObjectId());
			}
		}
		
		return ids;
	}
	
	private List<PowerStationGenerationSchedule> getSchedulesForPowerStations(List<Long> powerStationIds){
		List<PowerStationGenerationSchedule> schedules = new ArrayList<PowerStationGenerationSchedule>();
		
		for(long powerStationId: powerStationIds){
			schedules.add(calculator.getSchedule(powerStationId));
		}
		
		return schedules;
	}
	
	public boolean isObjectRegistered(long powerObjectId){
		return regesteredObjectsParameters.containsKey(powerObjectId);
	}
}
