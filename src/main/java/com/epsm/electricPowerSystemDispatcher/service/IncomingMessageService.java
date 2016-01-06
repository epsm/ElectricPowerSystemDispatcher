package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumerParametersStub;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationState;

public interface IncomingMessageService {
	void registerConsumer(ConsumerParametersStub parameters);
	void acceptConsumerState(SavedConsumerState state);
	void registerPowerStation(PowerStationParameters parameters);
	void acceptPowerStationState(PowerStationState state);
}
