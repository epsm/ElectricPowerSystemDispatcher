package com.epsm.electricPowerSystemDispatcher.repository;

import java.util.List;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedGeneratorState;

public interface GeneratorStateDao {
	public List<SavedGeneratorState> getStatesByPowerStationNumber(int powerStationNumber);
	public void saveState(SavedGeneratorState state);
}
