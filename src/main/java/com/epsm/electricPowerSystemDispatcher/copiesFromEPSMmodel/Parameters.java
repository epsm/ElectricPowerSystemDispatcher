package com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel;

import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Parameters extends Message{
	public Parameters(long powerObjectId, LocalDateTime realTimeStamp, LocalTime simulationTimeStamp) {
		super(powerObjectId, realTimeStamp, simulationTimeStamp);
	}
}