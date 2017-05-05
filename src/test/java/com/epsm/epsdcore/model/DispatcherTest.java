package com.epsm.epsdcore.model;

import com.epsm.epsmcore.model.consumption.ConsumerPermission;
import com.epsm.epsmcore.model.generation.PowerStationGenerationSchedule;
import com.epsm.epsmcore.model.generation.PowerStationParameters;
import com.epsm.epsmcore.model.generation.PowerStationState;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DispatcherTest {

	@InjectMocks
	private Dispatcher dispatcher;

	@Mock
	private PowerObjectStorage objectStorage;

	@Mock
	private ScheduleCalculator scheduleCalculator;

	@Mock
	private StateSaver saver;

	@Mock
	private ObjectsConnector connector;

	@Mock
	private PowerObjectsDateTimeSource dateTimeSource;

	@Mock
	private PowerStationParameters parameters;

	@Mock
	private PowerStationState state;

	@Mock
	private PowerStationGenerationSchedule generationSchedule;

	private final long OBJECT_ID = 6464;
	private final LocalDateTime REAL_TIMESTAMP = LocalDateTime.MIN;
	private final float FREQUENCY = 100;

	@Before
	public void setUp() {
		when(scheduleCalculator.getSchedules(any(), any(), any())).thenReturn(asList(generationSchedule));
	}

	@Test
	public void sendsGenerationSchedulesIfItIsTime(){
		makeTimeToSendSchedule();

		dispatcher.processSchedules();

		verify(connector).send(generationSchedule);
	}

	private void makeTimeNotToSendSchedule(){
		when(dateTimeSource.getSimulationDateTime()).thenReturn(LocalDateTime.MIN);
	}

	private void makeTimeToSendSchedule(){
		when(dateTimeSource.getSimulationDateTime()).thenReturn(LocalDateTime.MAX);
	}
	
	@Test
	public void doNotSendSchedulesIfItIsNotTimeTime(){
		makeTimeNotToSendSchedule();
		
		dispatcher.processSchedules();
		
		verify(connector, never()).send(isA(ConsumerPermission.class));
		verify(connector, never()).send(any(PowerStationGenerationSchedule.class));
	}
	
	@Test
	public void doNotSendSchedulesIfDateAppropriateButTimeNot(){
		makeAppropriateDateButUnappropriateTime();
		
		dispatcher.processSchedules();

		verify(connector, never()).send(isA(ConsumerPermission.class));
		verify(connector, never()).send(any(PowerStationGenerationSchedule.class));
	}
	
	private void makeAppropriateDateButUnappropriateTime(){
		LocalDate date = LocalDate.MAX;
		LocalTime time = Constants.TIME_TO_SEND_SCHEDULES.minusNanos(1);
		
		when(dateTimeSource.getSimulationDateTime()).thenReturn(LocalDateTime.of(date, time));
	}
}
