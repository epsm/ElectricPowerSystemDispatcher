package com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel;

public interface Dispatcher {
	void establishConnection(Parameters parameters);
	void acceptState(State state);
}
