package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

public class MiltiTimerTest {
	private TimeService timeService; 
	private MultiTimer multitimer;
	private LocalDateTime startTestTime;
	
	@Before
	public void initialize(){
		timeService = mock(TimeService.class);
		multitimer = new MultiTimer(timeService);
	
		when(timeService.getCurrentTime()).thenReturn(LocalDateTime.of(2000, 01, 01, 00, 00));
		startTestTime = timeService.getCurrentTime();
	}
	
	@Test
	public void multitimerKeepsTimersWhileTheyNotOutOfTime(){
		multitimer.startOrUpdateDelayOnTimeNumber(99);
		multitimer.startOrUpdateDelayOnTimeNumber(202);
		currentTimePlusTimeLessThenTimeout();
		multitimer.doRealTimeDependOperation();
		
		Assert.assertTrue(multitimer.getActiveTimers().contains(99L));
		Assert.assertTrue(multitimer.getActiveTimers().contains(202L));
		Assert.assertFalse(multitimer.getActiveTimers().contains(777L));
	}
	
	private void currentTimePlusTimeLessThenTimeout(){
		when(timeService.getCurrentTime()).thenReturn(startTestTime.plusSeconds(
				Constants.ACCEPTABLE_PAUSE_BETWEEN_RECEIVED_MESSAGES_IN_SECONDS - 1));
	}
	
	@Test
	public void multitimerDeletesTimersWhenTheirTimeIsOut(){
		multitimer.startOrUpdateDelayOnTimeNumber(99);
		currentTimePlusTimeMoreThanTimeout();
		multitimer.doRealTimeDependOperation();
		
		Assert.assertEquals(multitimer.getActiveTimers().size(), 0);
	}
	
	private void currentTimePlusTimeMoreThanTimeout(){
		when(timeService.getCurrentTime()).thenReturn(startTestTime.plusSeconds(
				Constants.ACCEPTABLE_PAUSE_BETWEEN_RECEIVED_MESSAGES_IN_SECONDS + 1));
	}
	
	@Test
	public void multitimerRefreshesTimeoutForActiveTimers(){
		multitimer.startOrUpdateDelayOnTimeNumber(99);
		currentTimePlusTimeLessThenTimeout();
		multitimer.startOrUpdateDelayOnTimeNumber(99);
		multitimer.doRealTimeDependOperation();
		
		Assert.assertTrue(multitimer.getActiveTimers().contains(99L));
	}
	
	@Test
	public void multitimerReturnsTrueIfRequestedTimerActive(){
		multitimer.startOrUpdateDelayOnTimeNumber(99);
		multitimer.isTimerActive(1);
		
		Assert.assertTrue(multitimer.isTimerActive(99L));
	}
	
	@Test
	public void multitimerReturnsFalseIfRequestedTimerNotActive(){
		multitimer.isTimerActive(1);
		
		Assert.assertFalse(multitimer.isTimerActive(99));
	}
}
