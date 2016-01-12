package com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel;

import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Command extends Message{
	public Command(long powerObjectId, LocalDateTime realTimeStamp, LocalTime simulationTimeStamp) {
		super(powerObjectId, realTimeStamp, simulationTimeStamp);
	}
}
