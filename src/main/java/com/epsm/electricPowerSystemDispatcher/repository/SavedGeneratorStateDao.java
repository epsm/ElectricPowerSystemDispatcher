package com.epsm.electricPowerSystemDispatcher.repository;

import java.util.List;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedGeneratorState;

public interface SavedGeneratorStateDao {
	public List<SavedGeneratorState> getStatesByPowerStationNumber(long powerStationNumber);
	public void saveState(SavedGeneratorState state);
}
