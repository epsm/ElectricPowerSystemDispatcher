package com.epsm.epsdCore.model;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.epsm.epsmCore.model.consumption.ConsumerParametersStub;
import com.epsm.epsmCore.model.dispatch.Parameters;
import com.epsm.epsmCore.model.generation.PowerStationParameters;

public class Constants {
	public static final LocalTime HOUR_TO_SEND_MESSAGE = LocalTime.of(18,00);
	public static final List<Class<? extends Parameters>> SUPPORTED_POWER_OBJECT_PARAMETERS = 
		    Collections.unmodifiableList(Arrays.asList(
		    PowerStationParameters.class, ConsumerParametersStub.class));
}
