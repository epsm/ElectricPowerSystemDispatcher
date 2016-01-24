package com.epsm.epsdCore.model;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.epsm.epsmCore.model.bothConsumptionAndGeneration.Message;

public class PowerObjectsDateTimeSource {
	private volatile LocalDateTime powerObjectsDateTime = LocalDateTime.MIN;
	private Logger logger = LoggerFactory.getLogger(PowerObjectsDateTimeSource.class);
	
	public void updateObjectsDateTime(Message objectMessage){
		LocalDateTime objectDateTime = objectMessage.getSimulationTimeStamp();
		LocalDateTime currentPowerObjectsDateTime = powerObjectsDateTime;
		
		if(objectDateTime.isAfter(powerObjectsDateTime)){
			powerObjectsDateTime = objectDateTime;
			logger.debug("Updated: powerObjectsDateTime from {} to {}.",
					currentPowerObjectsDateTime, objectDateTime);
		}
	}
	
	public LocalDateTime getPowerObjectsDateTime() {
		return powerObjectsDateTime;
	}
}
