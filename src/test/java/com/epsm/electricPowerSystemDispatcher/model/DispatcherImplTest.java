package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;

public class DispatcherImplTest {
	private DispatcherStub dispatcher;
	private ArgumentCaptor<PowerStationGenerationSchedule> stateCaptor;
	
	@Before
	public void initialize(){
		dispatcher = new DispatcherStub();
		stateCaptor = ArgumentCaptor.forClass(PowerStationGenerationSchedule.class);
	}
	
	@Test
	public void dispatcherSendsNotNullScheduleToPowerStation(){
		
	}
	
	@Test
	public void dispatcherRegisteresPowerStations(){
		
	}
	
	@Test
	public void dispatcherRegisteresConsumers(){
		
	}
	
	@Test
	public void dispatcherRejectRegistrationIfPowerStateAlreadyRegistered(){
		
	}
	
	@Test
	public void dispatcherRejectRegistrationIfConsumerAlreadyRegistered(){
		
	}
	
	@Test
	public void exceptionIfAcceptedStateFromNotRegisteredConsumer(){
		
	}
	
	@Test
	public void exceptionIfAcceptedStateFromNotRegisteredPowerStation(){
		
	}
}
