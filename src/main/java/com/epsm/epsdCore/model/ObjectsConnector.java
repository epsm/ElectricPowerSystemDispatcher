package com.epsm.epsdCore.model;

import com.epsm.epsmCore.model.dispatch.Command;

public interface ObjectsConnector {
	void sendCommand(Command command);
}
