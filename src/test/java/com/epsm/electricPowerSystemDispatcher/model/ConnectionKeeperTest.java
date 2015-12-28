package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

public class ConnectionKeeperTest {
	private TimeService timeService; 
	private ConnectionKeeper container;
	private LocalDateTime startTestTime;
	
	@Before
	public void initialize(){
		timeService = mock(TimeService.class);
		container = new ConnectionKeeper(timeService);
	
		when(timeService.getCurrentTime()).thenReturn(LocalDateTime.of(2000, 01, 01, 00, 00));
		startTestTime = timeService.getCurrentTime();
	}
	
	@Test
	public void containerStoresConnectionsWhileTheyNotOutOfTime(){
		container.addOrUpdateConnection(99L);
		container.addOrUpdateConnection(202L);
		plusTimeLessThenTimeout();
		container.doRealTimeDependOperation();
		
		Assert.assertTrue(container.getActiveConnections().contains(99L));
		Assert.assertTrue(container.getActiveConnections().contains(202L));
		Assert.assertFalse(container.getActiveConnections().contains(777L));
	}
	
	private void plusTimeLessThenTimeout(){
		when(timeService.getCurrentTime()).thenReturn(startTestTime.plusSeconds(
				Constants.PAUSE_BETWEEN_SENDING_MESSAGE_IN_SECONDS - 1));
	}
	
	@Test
	public void containerDeletesObjectsWhenConnectionTimeoutIsOver(){
		container.addOrUpdateConnection(99);
		plusTimeMoreThanTimeout();
		container.doRealTimeDependOperation();
		
		Assert.assertEquals(container.getActiveConnections().size(), 0);
	}
	
	private void plusTimeMoreThanTimeout(){
		when(timeService.getCurrentTime()).thenReturn(startTestTime.plusSeconds(
				Constants.PAUSE_BETWEEN_SENDING_MESSAGE_IN_SECONDS + 1));
	}
	
	@Test
	public void containerRefreshesTimeoutForStoredObjects(){
		container.addOrUpdateConnection(99L);
		plusTimeMoreThanTimeout();
		container.addOrUpdateConnection(99L);
		container.doRealTimeDependOperation();
		
		Assert.assertTrue(container.getActiveConnections().contains(99L));
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
