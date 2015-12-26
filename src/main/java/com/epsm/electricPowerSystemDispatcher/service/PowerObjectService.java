package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

public interface PowerObjectService {
	void registerConsumer(int consumerNumber);
	void acceptConsumerState(SavedConsumerState state);
	void registerPowerStation(PowerStationParameters parameters);
	void acceptPowerStationState(PowerStationState state);
}