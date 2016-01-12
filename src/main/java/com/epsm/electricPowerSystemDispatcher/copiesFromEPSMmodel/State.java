package com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel;

import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class State extends Message{
	public State(long powerObjectId, LocalDateTime realTimeStamp, LocalTime simulationTimeStamp) {
		super(powerObjectId, realTimeStamp, simulationTimeStamp);
	}
}
