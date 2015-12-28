package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.DispatcherService;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

@RunWith(MockitoJUnitRunner.class)
public class DispatcherStubImplTest {
	
	@InjectMocks
	private DispatcherStub dispatcher;
	
	@Mock
	private DispatcherService service;
	
	@Ignore
	@Test
	public void acceptsAndSaveToDBMesagesFromPowerStation(){
		
	}
	
	@Ignore
	@Test
	public void acceptsAndSaveToDBMesagesFromConsumer(){
		
	}
	
	@Ignore
	@Test
	public void sendingMesagesToPowerStationWithActiveConnections(){
		
	}
	
	@Ignore
	@Test
	public void sendingMesagesToConsumerWithActiveConnections(){
		
	}
}
