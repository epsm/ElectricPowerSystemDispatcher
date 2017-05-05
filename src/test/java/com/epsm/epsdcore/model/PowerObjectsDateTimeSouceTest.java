package com.epsm.epsdcore.model;

import com.epsm.epsmcore.model.common.State;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PowerObjectsDateTimeSouceTest {
	private PowerObjectsDateTimeSource source;
	private State message;
	
	@Before
	public void setUp(){
		source = new PowerObjectsDateTimeSource();
		message = mock(State.class);
	}
	
	@Test
	public void getPowerObjectsDateTimeMethodReturnsLocalDateTimeMinOriginally(){
		Assert.assertEquals(LocalDateTime.MIN, source.getSimulationDateTime());
	}
	
	@Test
	public void updateObjectsDateTimeMethodUpdatesDateTime(){
		when(message.getSimulationTimeStamp()).thenReturn(LocalDateTime.MAX);
		source.updateObjectsDateTime(message);
		
		Assert.assertEquals(LocalDateTime.MAX, source.getSimulationDateTime());
	}
	
	@Test
	public void timeDoesNotUpdateInBackDirection(){
		updateTimeForwardThenBackward();
		
		Assert.assertEquals(LocalDateTime.MAX, source.getSimulationDateTime());
	}
	
	private void updateTimeForwardThenBackward(){
		when(message.getSimulationTimeStamp()).thenReturn(LocalDateTime.MAX);
		source.updateObjectsDateTime(message);
		when(message.getSimulationTimeStamp()).thenReturn(LocalDateTime.MIN);
		source.updateObjectsDateTime(message);
	}
}
