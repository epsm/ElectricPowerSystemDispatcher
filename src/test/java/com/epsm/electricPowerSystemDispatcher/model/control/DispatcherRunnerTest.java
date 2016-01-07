package com.epsm.electricPowerSystemDispatcher.model.control;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class DispatcherRunnerTest{
	private DispatcherRunner runner = new DispatcherRunner(); 
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void exceptionIfSimulationIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("DispatcherRunner: dispatcher must not be null.");
		
		runner.runDispatcher(null);
	}
	
}
