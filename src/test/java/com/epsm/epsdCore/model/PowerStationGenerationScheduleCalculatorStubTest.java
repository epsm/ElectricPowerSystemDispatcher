package com.epsm.epsdCore.model;

import org.junit.Assert;
import org.junit.Test;

import com.epsm.epsmCore.model.generalModel.TimeService;
import com.epsm.epsmCore.model.generation.PowerStationGenerationSchedule;

public class PowerStationGenerationScheduleCalculatorStubTest {
	private TimeService timeService = new TimeService();
	
	@Test(expected = IllegalArgumentException.class)
	public void exceptionInConstructorIfTimeserviceIsNull(){
		new PowerStationGenerationScheduleCalculatorStub(null);
	}
}
