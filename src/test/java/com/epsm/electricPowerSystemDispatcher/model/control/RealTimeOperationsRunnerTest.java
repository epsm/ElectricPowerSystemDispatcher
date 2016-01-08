package com.epsm.electricPowerSystemDispatcher.model.control;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RealTimeOperationsRunnerTest{
	private RealTimeOperationsRunner runner = new RealTimeOperationsRunner(); 
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void exceptionIfSimulationIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("RealTimeOperationsRunner: object must not be null.");
		
		runner.runDispatcher(null);
	}
}
