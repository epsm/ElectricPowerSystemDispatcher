package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.ConsumerParametersStub;
import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.ConsumerState;
import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.PowerStationParameters;
import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.PowerStationState;

public interface IncomingMessageService {
	void establishConnectionWithConsumer(ConsumerParametersStub parameters);
	void acceptConsumerState(ConsumerState state);
	void establishConnectionWithPowerStation(PowerStationParameters parameters);
	void acceptPowerStationState(PowerStationState state);
}
