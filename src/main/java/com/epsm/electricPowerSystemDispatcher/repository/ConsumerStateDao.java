package com.epsm.electricPowerSystemDispatcher.repository;

import java.util.List;

import com.epsm.electricPowerSystemDispatcher.domain.ConsumerState;

public interface ConsumerStateDao {
	public List<ConsumerState> getStatesByNumber(int consumerNumber);
	public void saveState(ConsumerState state);
}
