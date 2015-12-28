package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

public class ActiveConnectionContainerTest {
	private TimeService timeService; 
	private ActiveConnectionContainer container;
	private LocalDateTime startTestTime;
	
	@Before
	public void initialize(){
		timeService = mock(TimeService.class);
		container = new ActiveConnectionContainer(timeService);
	
		when(timeService.getCurrentTime()).thenReturn(LocalDateTime.of(2000, 01, 01, 00, 00));
		startTestTime = timeService.getCurrentTime();
	}
	
	@Test
	public void containerStoresConnectionsWhileTheyNotOutOfTime(){
		container.addOrUpdateConnection(99);
		container.addOrUpdateConnection(202);
		plusTimeLessThenTimeout();
		container.manageConnections();
		
		Assert.assertTrue(container.getActiveConnections().contains(99));
		Assert.assertTrue(container.getActiveConnections().contains(202));
		Assert.assertFalse(container.getActiveConnections().contains(777));
	}
	
	private void plusTimeLessThenTimeout(){
		when(timeService.getCurrentTime()).thenReturn(startTestTime.plusSeconds(
				Constants.CONNECTION_TIMEOUT_IN_SECONDS - 1));
	}
	
	@Test
	public void containerDeletesObjectsWhenConnectionTimeoutIsOver(){
		container.addOrUpdateConnection(99);
		plusTimeMoreThanTimeout();
		container.manageConnections();
		
		Assert.assertEquals(container.getActiveConnections().size(), 0);
	}
	
	private void plusTimeMoreThanTimeout(){
		when(timeService.getCurrentTime()).thenReturn(startTestTime.plusSeconds(
				Constants.CONNECTION_TIMEOUT_IN_SECONDS + 1));
	}
	
	@Test
	public void containerRefreshesTimeoutForStoredObjects(){
		container.addOrUpdateConnection(99);
		plusTimeMoreThanTimeout();
		container.addOrUpdateConnection(99);
		container.manageConnections();
		
		Assert.assertTrue(container.getActiveConnections().contains(99));
	}
	
	@Test
	public void containerReturnsTrueIfConnectionActive(){
		container.addOrUpdateConnection(99);
		container.isConnectionActive(1);
		
		Assert.assertTrue(container.isConnectionActive(99));
	}
	
	@Test
	public void containerReturnsFalseIfConnectionNotActive(){
		container.isConnectionActive(1);
		
		Assert.assertFalse(container.isConnectionActive(99));
	}
}
