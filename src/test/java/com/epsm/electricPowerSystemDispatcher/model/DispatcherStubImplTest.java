package com.epsm.electricPowerSystemDispatcher.model;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

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
	
	@Mock
	private ConnectionKeeper keeper;
	
	@Test
	public void establishesConnectionWithPowerStations(){
		dispatcher.establishConnectionWithPowerStation(1);
		
		verify(keeper).addOrUpdatePowerStationConnection(1);
	}
	
	@Test
	public void establishesConnectionWithConsumer(){
		dispatcher.establishConnectionWithConsumer(2);
		
		verify(keeper).addOrUpdateConsumerConnection(2);
	}
	
	@Test
	public void acceptsAndSaveToDBMesagesFromPowerStationIfConnectionEstablished(){
		dispatcher.establishConnectionWithConsumer(2);
		dispatcher.acceptPowerStationState(null);
		
		verify(service).savePowerStationState(any());
	}
	
	
	@Test
	public void doNothingIfAcceptedPowerStationMessageWasFromDisconnectedPowerStation(){
		dispatcher.acceptPowerStationState(null);
		
		verify(service, never()).savePowerStationState(any());
	}
	
	@Ignore
	@Test
	public void acceptsAndSaveToDBMesagesFromConsumer(){
		
	}
	
	@Ignore
	@Test
	public void sendsNotNulScheduleToPowerStationWithActiveConnectionsIfConnectionEstablished(){
		
	}
	
	@Ignore
	@Test
	public void doNothingIfAcceptConsumerMessageFromDisconnectedConsumer(){
		
	}
	
	@Ignore
	@Test
	public void sendsMesagesToConsumerWithActiveConnections(){
		
	}
}
