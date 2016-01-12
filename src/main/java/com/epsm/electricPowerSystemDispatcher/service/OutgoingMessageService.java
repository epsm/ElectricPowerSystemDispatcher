package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.Command;

public interface OutgoingMessageService {
	void sendCommand(Command command);
}
