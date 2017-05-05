package com.epsm.epsdcore.model;


import com.epsm.epsmcore.model.consumption.ConsumerPermission;
import com.epsm.epsmcore.model.generation.PowerStationGenerationSchedule;

public interface ObjectsConnector {
	void send(ConsumerPermission permission);
	void send(PowerStationGenerationSchedule schedule);
}
