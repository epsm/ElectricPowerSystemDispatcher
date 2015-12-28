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
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.DispatcherService;
import com.epsm.electricPowerSystemDispatcher.service.PowerObjectService;
import com.epsm.electricPowerSystemModel.model.dispatch.ConsumerParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerObjectParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerObjectState;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationParameters;
import com.epsm.electricPowerSystemModel.model.dispatch.PowerStationState;

@RunWith(MockitoJUnitRunner.class)
public class DispatcherStubImplTest {
	PowerObjectParameters parameters;
	PowerObjectState state;
	private final long POWER_OBJECT_ID = 7676;
	
	@InjectMocks
	private DispatcherStub dispatcher;
	
	@Mock
	private DispatcherService dispatcherService;
	
	@Mock
	private PowerObjectService powerObjectService;
	
	@Mock
	private ConnectionKeeper keeper;
	
	@Before
	public void initialize(){
		parameters = mock(PowerObjectParameters.class);
		state = mock(PowerObjectState.class);
		when(parameters.getPowerObjectId()).thenReturn(POWER_OBJECT_ID);
		when(state.getPowerObjectId()).thenReturn(POWER_OBJECT_ID);
	}
	
	@Ignore
	@Test
	public void establishesConnectionWithPowerStations(){
		dispatcher.establishConnection(parameters);
		
		verify(keeper).addOrUpdateConnection(POWER_OBJECT_ID);
	}
	
	@Test
	public void acceptsAndSaveToDBMesagesFromPowerObjectIfConnectionEstablished(){
		when(keeper.isConnectionActive(eq(POWER_OBJECT_ID))).thenReturn(true);
		dispatcher.acceptState(state);
		
		verify(powerObjectService).savePowerObjectState(state);
	}
	

	@Test
	public void doNothingIfAcceptedPowerStationMessageWasFromDisconnectedPowerStation(){
		when(keeper.isConnectionActive(eq(POWER_OBJECT_ID))).thenReturn(false);
		dispatcher.acceptState(state);
		
		verify(powerObjectService, never()).savePowerObjectState(state);
	}
	
	@Ignore
	@Test
	public void sendsNotNulScheduleToPowerStationWithActiveConnectionsIfConnectionEstablished(){
		
	}
	
	@Ignore
	@Test
	public void sendsNotNulMessagesToConsumersWithActiveConnectionsIfConnectionEstablished(){
		
	}

	@Ignore
	@Test
	public void sendsNothingIfConnectionEstablishedButItIsNotTimeToSend(){
		
	}
}
