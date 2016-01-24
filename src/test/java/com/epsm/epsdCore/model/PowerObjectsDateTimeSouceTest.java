package com.epsm.epsdCore.model;

import static org.mockito.Mockito.*;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.epsm.epsmCore.model.bothConsumptionAndGeneration.Message;

public class PowerObjectsDateTimeSouceTest {
	private PowerObjectsDateTimeSource source;
	private Message message;
	
	@Before
	public void setUp(){
		source = new PowerObjectsDateTimeSource();
		message = mock(Message.class);
	}
	
	@Test
	public void getPowerObjectsDateTimeMethodReturnsLocalDateTimeMinOriginally(){
		Assert.assertEquals(LocalDateTime.MIN, source.getPowerObjectsDateTime());
	}
	
	@Test
	public void updateObjectsDateTimeMethodUpdatesDateTime(){
		when(message.getSimulationTimeStamp()).thenReturn(LocalDateTime.MAX);
		source.updateObjectsDateTime(message);
		
		Assert.assertEquals(LocalDateTime.MAX, source.getPowerObjectsDateTime());
	}
	
	@Test
	public void timeDoesNotUpdateInBackDirection(){
		updateTimeForwardThenBackward();
		
		Assert.assertEquals(LocalDateTime.MAX, source.getPowerObjectsDateTime());
	}
	
	private void updateTimeForwardThenBackward(){
		when(message.getSimulationTimeStamp()).thenReturn(LocalDateTime.MAX);
		source.updateObjectsDateTime(message);
		when(message.getSimulationTimeStamp()).thenReturn(LocalDateTime.MIN);
		source.updateObjectsDateTime(message);
	}
}
