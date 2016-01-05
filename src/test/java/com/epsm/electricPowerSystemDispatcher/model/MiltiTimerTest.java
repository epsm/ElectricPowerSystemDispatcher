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
	private final int TIME_DEALY = 10;
	
	@Before
	public void initialize(){
		timeService = mock(TimeService.class);
		multitimer = new MultiTimer(timeService, TIME_DEALY);
	
		when(timeService.getCurrentTime()).thenReturn(LocalDateTime.of(2000, 01, 01, 00, 00));
		startTestTime = timeService.getCurrentTime();
	}
	
	@Test
	public void multitimerKeepsTimersWhileTheyNotOutOfTime(){
		multitimer.startOrUpdateDelayOnTimerNumber(99);
		multitimer.startOrUpdateDelayOnTimerNumber(202);
		currentTimePlusTimeLessThenTimeout();
		
		Assert.assertTrue(multitimer.getActiveTimers().contains(99L));
		Assert.assertTrue(multitimer.getActiveTimers().contains(202L));
		Assert.assertFalse(multitimer.getActiveTimers().contains(777L));
	}
	
	private void currentTimePlusTimeLessThenTimeout(){
		when(timeService.getCurrentTime()).thenReturn(startTestTime.plusSeconds(TIME_DEALY - 1));
	}
	
	@Test
	public void multitimerDeletesTimersWhenTheirTimeIsOut(){
		multitimer.startOrUpdateDelayOnTimerNumber(99);
		currentTimePlusTimeMoreThanTimeout();
		
		Assert.assertEquals(multitimer.getActiveTimers().size(), 0);
	}
	
	private void currentTimePlusTimeMoreThanTimeout(){
		when(timeService.getCurrentTime()).thenReturn(startTestTime.plusSeconds(TIME_DEALY + 1));
	}
	
	@Test
	public void multitimerRefreshesTimeoutForActiveTimers(){
		multitimer.startOrUpdateDelayOnTimerNumber(99);
		currentTimePlusTimeLessThenTimeout();
		multitimer.startOrUpdateDelayOnTimerNumber(99);
		
		Assert.assertTrue(multitimer.getActiveTimers().contains(99L));
	}
	
	@Test
	public void multitimerReturnsTrueIfRequestedTimerActive(){
		multitimer.startOrUpdateDelayOnTimerNumber(99);
		multitimer.isTimerActive(1);
		
		Assert.assertTrue(multitimer.isTimerActive(99L));
	}
	
	@Test
	public void multitimerReturnsFalseIfRequestedTimerNotActive(){
		multitimer.isTimerActive(1);
		
		Assert.assertFalse(multitimer.isTimerActive(99));
	}
}
