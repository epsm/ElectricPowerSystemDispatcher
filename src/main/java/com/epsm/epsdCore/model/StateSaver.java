package com.epsm.epsdCore.model;

import com.epsm.epsmCore.model.dispatch.State;

public interface StateSaver {
	void savePowerObjectState(State state);
}
