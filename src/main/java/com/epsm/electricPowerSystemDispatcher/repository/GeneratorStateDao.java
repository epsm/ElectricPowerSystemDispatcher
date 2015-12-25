package com.epsm.electricPowerSystemDispatcher.repository;

import java.util.List;

import com.epsm.electricPowerSystemDispatcher.domains.GeneratorState;

public interface GeneratorStateDao {
	public List<GeneratorState> getStates(int powerStationState);
	public void saveState(GeneratorState state);
}
