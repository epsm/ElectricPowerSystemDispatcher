package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemModel.model.consumption.ConsumerParametersStub;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumerState;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationState;

public interface IncomingMessageService {
	void establishConnectionWithConsumer(ConsumerParametersStub parameters);
	void acceptConsumerState(ConsumerState state);
	void establishConnectionWithPowerStation(PowerStationParameters parameters);
	void acceptPowerStationState(PowerStationState state);
}
