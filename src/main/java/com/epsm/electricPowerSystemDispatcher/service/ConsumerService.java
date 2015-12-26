package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemDispatcher.model.domain.ConsumerState;

public interface ConsumerService {
	public void sendConfirmationToConsumer(int number);
	public void saveState(ConsumerState state);
}
