package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemModel.model.dispatch.State;

public interface PowerObjectService {
	void savePowerObjectState(State state);
}
