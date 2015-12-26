package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

public interface PowerStationService {
	public void sendConfirmationToPowerStation(int powerStationNumbernumber);
	public void sendGenerationScheduleToPowerStation(
			int powerStationNumber, PowerStationGenerationSchedule schedule);
	public void saveState(PowerStationState state);
}
