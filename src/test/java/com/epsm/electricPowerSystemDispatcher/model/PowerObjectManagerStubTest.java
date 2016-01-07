package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.epsm.electricPowerSystemModel.model.consumption.ConsumerParametersStub;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumptionPermissionStub;
import com.epsm.electricPowerSystemModel.model.dispatch.Parameters;
import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationParameters;

public class PowerObjectManagerStubTest {
	private TimeService timeService;
	private DispatcherImpl dispatcher;
	private PowerObjectManagerStub manager;
	private PowerStationParameters powerStationParameters;
	private ConsumerParametersStub consumerParameters;
	private Parameters unknownParameters;
	private final int POWER_OBJECT_ID = 468;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Before
	public void setUp(){
		timeService = new TimeService();
		dispatcher = mock(DispatcherImpl.class);
		manager = spy(new PowerObjectManagerStub(timeService, dispatcher));
		powerStationParameters = new PowerStationParameters(POWER_OBJECT_ID, LocalDateTime.MIN,
				LocalTime.MIN, 2);
		consumerParameters = new ConsumerParametersStub(POWER_OBJECT_ID, LocalDateTime.MIN,
				LocalTime.MIN);
		unknownParameters = new UnknownParameters(POWER_OBJECT_ID, LocalDateTime.MIN,
				LocalTime.MIN);
	}
	
	private class UnknownParameters extends Parameters{
		public UnknownParameters(long powerObjectId, LocalDateTime realTimeStamp, LocalTime simulationTimeStamp) {
			super(powerObjectId, realTimeStamp, simulationTimeStamp);
		}

		@Override
		public String toString() {
			return null;
		}
	}
	
	@Test
	public void exceptionInConstructorIfTimeServiceIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("PowerObjectManagerStub constructor: timeService can't be null.");
	
	    new PowerObjectManagerStub(null, dispatcher);
	}
	
	@Test
	public void exceptionInConstructorIfDispatcherIsNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("PowerObjectManagerStub constructor: dispatcher can't be null.");
	
	    new PowerObjectManagerStub(timeService, null);
	}
	
	@Test
	public void managerSendsPowerStationGenerationScheduleToKnownPowerStations(){
		manager.rememberObjectIfItTypeIsKnown(powerStationParameters);
		manager.sendMessage(POWER_OBJECT_ID);
		
		verify(dispatcher).sendCommand(isA(PowerStationGenerationSchedule.class));
	}
	
	@Test
	public void managerSendsConsumptionPermissionToKnownConsumers(){
		manager.rememberObjectIfItTypeIsKnown(consumerParameters);
		manager.sendMessage(POWER_OBJECT_ID);
		
		verify(dispatcher).sendCommand(isA(ConsumptionPermissionStub.class));
	}
	
	@Test
	public void managerSendsNothingToUnknownPowerObject(){
		manager.rememberObjectIfItTypeIsKnown(unknownParameters);
		manager.sendMessage(POWER_OBJECT_ID);
		
		verify(dispatcher, never()).sendCommand(any());
	}
}
