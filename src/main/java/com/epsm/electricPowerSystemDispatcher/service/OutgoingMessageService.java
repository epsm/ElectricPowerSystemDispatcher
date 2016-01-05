package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemModel.model.dispatch.Command;

public interface OutgoingMessageService {
	void sendCommand(Command command);
}
