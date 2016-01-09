package com.epsm.electricPowerSystemDispatcher.service;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

import com.epsm.electricPowerSystemModel.model.consumption.ConsumptionPermissionStub;
import com.epsm.electricPowerSystemModel.model.dispatch.Command;
import com.epsm.electricPowerSystemModel.model.generation.PowerStationGenerationSchedule;
import com.epsm.electricPowerSystemModel.util.UrlRequestSender;

@RunWith(MockitoJUnitRunner.class)
public class OutgoingMessageServiceImplTest {
	private PowerStationGenerationSchedule schedule;
	private ConsumptionPermissionStub permission;
	
	@InjectMocks
	private OutgoingMessageServiceImpl service;
	
	@Mock
	private UrlRequestSender<ConsumptionPermissionStub> permissionSender;
	
	@Mock
	private UrlRequestSender<PowerStationGenerationSchedule> scheduleSender;
	
	@Rule
	public ExpectedException expectedEx = ExpectedException.none();
	
	@Test
	public void exceptionIfCommandisNull(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("Command must not be null.");
		
		service.sendCommand(null);
	}
	
	@Test
	public void sendsPowerStationGenerationScheduleWithScheduleSender(){
		schedule = new PowerStationGenerationSchedule(1, LocalDateTime.MIN, LocalTime.MIN, 1);
		
		service.sendCommand(schedule);
		
		verify(scheduleSender).sendObjectInJsonToUrlWithPOST(any(), eq(schedule));
	}
	
	@Test
	public void sendsPowerConsumerPermissioneWithPermissionSender(){
		permission = new ConsumptionPermissionStub(1, LocalDateTime.MIN, LocalTime.MIN);
		
		service.sendCommand(permission);
		
		verify(permissionSender).sendObjectInJsonToUrlWithPOST(any(), eq(permission));
	}
	
	@Test
	public void exceptionIfCommandUnsuported(){
		expectedEx.expect(IllegalArgumentException.class);
	    expectedEx.expectMessage("Unsuported type of Command: UnsupportedCommand.");
		
		service.sendCommand(new UnsupportedCommand(1, LocalDateTime.MIN, LocalTime.MIN));
	}
	
	private class UnsupportedCommand extends Command{
		public UnsupportedCommand(long powerObjectId, LocalDateTime realTimeStamp, LocalTime simulationTimeStamp) {
			super(powerObjectId, realTimeStamp, simulationTimeStamp);
		}

		@Override
		public String toString() {
			return null;
		}
	}
}
