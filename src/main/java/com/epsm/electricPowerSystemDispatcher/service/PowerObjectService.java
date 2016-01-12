package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.State;

public interface PowerObjectService {
	void savePowerObjectState(State state);
}
