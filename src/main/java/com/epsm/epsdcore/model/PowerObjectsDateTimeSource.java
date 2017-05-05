package com.epsm.epsdcore.model;

import com.epsm.epsmcore.model.common.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PowerObjectsDateTimeSource {

	private volatile LocalDateTime powerObjectsDateTime = LocalDateTime.MIN;
	private Logger logger = LoggerFactory.getLogger(PowerObjectsDateTimeSource.class);
	
	public void updateObjectsDateTime(State message){
		LocalDateTime objectDateTime = message.getSimulationTimeStamp();
		LocalDateTime currentPowerObjectsDateTime = powerObjectsDateTime;
		
		if(objectDateTime.isAfter(powerObjectsDateTime)){
			powerObjectsDateTime = objectDateTime;
			logger.debug("Updated: powerObjectsDateTime from {} to {}.", currentPowerObjectsDateTime, objectDateTime);
		}
	}
	
	public LocalDateTime getSimulationDateTime() {
		return powerObjectsDateTime;
	}
}
