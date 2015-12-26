package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epsm.electricPowerSystemDispatcher.model.domain.ConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.DispatcherService;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

@RunWith(MockitoJUnitRunner.class)
public class DispatcherStubImplTest {
	private PowerStationParameters parameters;
	private PowerStationState powerStationState;
	private ConsumerState consumerState;
	
	@InjectMocks
	private DispatcherStub dispatcher;
	
	@Mock
	private DispatcherService service;
	
	private final int POWER_STATION_NUMBER = 1;
	private final int CONSUMER_NUMBER = 1;
	
	@Before
	public void initialize(){
		parameters = mock(PowerStationParameters.class);
		powerStationState = mock(PowerStationState.class);
		consumerState = mock(ConsumerState.class);
		
		when(parameters.getPowerStationNumber()).thenReturn(POWER_STATION_NUMBER);
		when(powerStationState.getPowerStationNumber()).thenReturn(POWER_STATION_NUMBER);
		when(consumerState.getConsumerNumber()).thenReturn(CONSUMER_NUMBER);
	}
	
	@Test
	public void dispatcherSendsConfirmatonToPowerStationsRightAfterRegistration(){
		dispatcher.registerPowerStation(parameters);
		
		verify(service, atLeastOnce()).sendConfirmationToPowerStation(POWER_STATION_NUMBER);
	}
	
	@Test
	public void dispatcherSendsConfirmatonToConsumerRightAfterRegistration(){
		dispatcher.registerConsumer(CONSUMER_NUMBER);
		
		verify(service, atLeastOnce()).sendConfirmationToConsumer(CONSUMER_NUMBER);
	}
	
	@Test
	public void dispatcherSavesPowerStationStateIfStationRegistered(){
		dispatcher.registerPowerStation(parameters);
		dispatcher.acceptPowerStationState(powerStationState);
		
		verify(service).savePowerStationState(powerStationState);
	}
	
	@Test
	public void dispatcherNotSavesPowerStationStateIfStationNotRegistered(){
		dispatcher.acceptPowerStationState(powerStationState);
		
		verify(service, never()).savePowerStationState(powerStationState);
	}
	
	@Test
	public void dispatcherSavesConsumerStateIfConsumernRegistered(){
		dispatcher.registerConsumer(CONSUMER_NUMBER);
		dispatcher.acceptConsumerState(consumerState);
		
		verify(service).saveConsumerState(consumerState);
	}
	
	@Test
	public void dispatcherNotSavesConsumerStatesIfConsumerNotRegistered(){
		dispatcher.acceptConsumerState(consumerState);
		
		verify(service, never()).saveConsumerState(consumerState);
	}
	
	@Test
	public void dispatcherSendingSchedulesToPowerStationAfterItsRegistration() throws InterruptedException{
		dispatcher.registerPowerStation(parameters);
		doPause();
		
		verify(service, atLeastOnce()).sendGenerationScheduleToPowerStation(
				eq(POWER_STATION_NUMBER), any(PowerStationGenerationSchedule.class));
	}
	
	private void doPause() throws InterruptedException{
		Thread.sleep(100);
	}
}
