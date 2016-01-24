package com.epsm.epsdCore.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		if(isPowerObjectSupported(parameters) && isPowerObjectIdUniqe(parameters)){
			registerPowerObject(parameters);
			logger.info("Registered: power object with parameters {}.", parameters);
			return true;
		}else{
			return false;
		}
	}
	
	private boolean isPowerObjectSupported(Parameters parameters){
		boolean supported = Constants.SUPPORTED_POWER_OBJECT_PARAMETERS.contains(
				parameters.getClass());
		
		if(!supported){
			logger.warn("PowerObject type with parameters {} is not supported.", parameters);
		}
		
		return supported; 
	}
	
	private boolean isPowerObjectIdUniqe(Parameters parameters){
		long powerObjectId = parameters.getPowerObjectId();
		boolean unique = !regesteredObjectsParameters.containsKey(powerObjectId);
		
		if(!unique){
			logger.warn("PowerObject id#{} is not unique.", powerObjectId);
		}
		
		return unique; 
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
