package com.epsm.electricPowerSystemDispatcher.repository;

import java.util.List;

import com.epsm.electricPowerSystemDispatcher.domain.GeneratorState;

public interface GeneratorStateDao {
	public List<GeneratorState> getStatesByPowerStationNumber(int powerStationNumber);
	public void saveState(GeneratorState state);
}