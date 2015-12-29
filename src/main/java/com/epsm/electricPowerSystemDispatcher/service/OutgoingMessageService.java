package com.epsm.electricPowerSystemDispatcher.service;

import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;

public interface OutgoingMessageService {
	void sendMessageToPowerStation(PowerStationGenerationSchedule schedule);
}
