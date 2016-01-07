package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

public class ConnectionManagerTest {
	private TimeService timeService;
	private ConnectionManager manager;
	private Set<Long> idToSendMessage;
	private final long ID = 68654L;
	
	@Before
	public void setUp(){
		timeService = mock(TimeService.class);
		when(timeService.getCurrentTime()).thenReturn(LocalDateTime.MIN);
		
		idToSendMessage = new HashSet<Long>();
		manager = new ConnectionManager(timeService);
	}
	
	@Test
	public void returnsIdIfItIsTimeToSendMessageForIt(){
		manager.refreshTimeout(ID);
		idToSendMessage = manager.getConnectionsForSendingCommand();
		
		Assert.assertTrue(idToSendMessage.contains(ID));
	}
	
	@Test
	public void doesNotReturnIdIfItIsNotTimeToSendMessageForIt(){
		manager.refreshTimeout(ID);
		manager.refreshLastSentCommandTime(ID);
		idToSendMessage = manager.getConnectionsForSendingCommand();
		
		Assert.assertEquals(0, idToSendMessage.size());
	}
}
