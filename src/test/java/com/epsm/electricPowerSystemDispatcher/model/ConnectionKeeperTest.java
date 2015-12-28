package com.epsm.electricPowerSystemDispatcher.model;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionKeeperTest {

	@InjectMocks
	private ConnectionKeeper keeper;
	
	@Mock
	private ActiveConnectionContainer powerStations;
	
	@Mock
	private ActiveConnectionContainer consumers;
	
	
	@Test
	public void keeperInvokesAddOrUpfateMethodsOnPowerStationContaine(){
		keeper.addOrUpdatePowerStationConnection(1);
		
		verify(powerStations).addOrUpdateConnection(1);
	}
	
	@Test
	public void keeperInvokesAddOrUpfateMethodsOnConsumerContainer(){
		keeper.addOrUpdateConsumerConnection(1);
		
		verify(consumers).addOrUpdateConnection(1);
	}
	
	@Test
	public void keeperInvokesManageConnectionsOnBothContainers(){
		keeper.manageConnections();
		
		verify(powerStations).manageConnections();
		verify(consumers).manageConnections();
	}
}
