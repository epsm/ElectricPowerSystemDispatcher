package com.epsm.epsdCore.model;

import org.junit.Test;

public class PowerStationGenerationScheduleCalculatorStubTest {
	
	@Test(expected = IllegalArgumentException.class)
	public void exceptionInConstructorIfTimeserviceIsNull(){
		new PowerStationGenerationScheduleCalculatorStub(null);
	}
}
