package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemModel.model.dispatch.PowerObjectState;

public interface PowerObjectService {
	void savePowerObjectState(PowerObjectState state);
}
