package com.epsm.epsdCore.model;

import java.time.LocalDateTime;

import com.epsm.epsmCore.model.dispatch.Command;

public interface ObjectsConnector {
	void sendCommand(Command command);
	LocalDateTime getDateTimeInSimulation();
}
