package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

public interface DispatcherService {
	void sendConfirmationToPowerStation(int powerStationNumber);
	void sendConfirmationToConsumer(int consumerNumber);
	void sendGenerationScheduleToPowerStation(
			int powerStationNumber, PowerStationGenerationSchedule schedule);
	void savePowerStationState(PowerStationState state);
	void saveConsumerState(SavedConsumerState state);
}
