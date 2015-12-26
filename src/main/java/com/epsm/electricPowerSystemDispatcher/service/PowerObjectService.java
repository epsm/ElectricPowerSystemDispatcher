package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemDispatcher.model.domain.ConsumerState;

public interface PowerObjectService {
	void registerConsumer(int consumerNumber);
	void acceptConsumerState(ConsumerState state);
}
