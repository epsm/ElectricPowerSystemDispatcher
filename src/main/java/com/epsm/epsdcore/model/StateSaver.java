package com.epsm.epsdcore.model;


import com.epsm.epsmcore.model.consumption.ConsumerState;
import com.epsm.epsmcore.model.generation.PowerStationState;

import java.util.List;

public interface StateSaver {

	void saveConsumerStates(List<ConsumerState> states);
	void savePowerStationStates(List<PowerStationState> states);
}
