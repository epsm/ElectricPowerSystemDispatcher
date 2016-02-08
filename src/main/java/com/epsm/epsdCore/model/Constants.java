package com.epsm.epsdCore.model;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.epsm.epsmCore.model.consumption.ConsumerParametersStub;
import com.epsm.epsmCore.model.dispatch.Parameters;
import com.epsm.epsmCore.model.generation.PowerStationParameters;

public class Constants {
	public static final int PAUSE_BETWEEN_REAL_TIME_STEPS_IN_MS = 500;
	public static final int LOG_EVERY_REALTIME_STEPS = 20;
	public static final LocalTime TIME_TO_SEND_SCHEDULES = LocalTime.of(18,00);
	public static final List<Class<? extends Parameters>> SUPPORTED_POWER_OBJECT_PARAMETERS = 
		    Collections.unmodifiableList(Arrays.asList(
		    PowerStationParameters.class, ConsumerParametersStub.class));
}
