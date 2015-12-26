package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;

public class ScheduleSendingTimerTest {
	private DispatcherStub dispatcher;
	private ScheduleSendingTimer timer;
	private final int POWER_STATION_NUMBER = 1;
	
	@Before
	public void initialize(){
		dispatcher = mock(DispatcherStub.class);
		timer = new ScheduleSendingTimer(dispatcher, POWER_STATION_NUMBER);
	}
	
	@Test
	public void senderSendsCorrectScheduleToSation() throws InterruptedException{
		timer.turnOn();
		doPause();
		
		verify(dispatcher,atLeastOnce()).sendGenerationScheduleToPowerStation(POWER_STATION_NUMBER);
	}
	
	private void doPause() throws InterruptedException{
		Thread.sleep(100);
	}
}
