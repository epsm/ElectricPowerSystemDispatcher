package com.epsm.epsdcore.model;

import com.epsm.epsmcore.model.consumption.ConsumerState;
import com.epsm.epsmcore.model.consumption.RandomLoadConsumerParameters;
import com.epsm.epsmcore.model.consumption.ScheduledLoadConsumerParameters;
import com.epsm.epsmcore.model.generation.PowerStationGenerationSchedule;
import com.epsm.epsmcore.model.generation.PowerStationParameters;
import com.epsm.epsmcore.model.generation.PowerStationState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class Dispatcher {

	@Autowired
	private ScheduleCalculator scheduleCalculator;

	@Autowired
	private PowerObjectStorage objectStorage;

	@Autowired
	private PowerObjectsDateTimeSource dateTimeSource;

	@Autowired
	private StateSaver saver;

	@Autowired
	private ObjectsConnector connector;

	private LocalDate sentDate = LocalDate.MIN;

	public void processSchedules() {
		LocalDateTime dateTimeInSimulation = dateTimeSource.getSimulationDateTime();

		if (isItTimeToSendSchedules(dateTimeInSimulation)) {
			List<PowerStationGenerationSchedule> schedules = calculateSchedules();
			sendSchedules(schedules);
			sentDate = dateTimeInSimulation.toLocalDate();
		}
	}

	private boolean isItTimeToSendSchedules(LocalDateTime dateTimeInSimulation) {
		return sentDate.isBefore(dateTimeInSimulation.toLocalDate())
				&& dateTimeInSimulation.toLocalTime().isAfter(Constants.TIME_TO_SEND_SCHEDULES);
	}

	private List<PowerStationGenerationSchedule> calculateSchedules() {
		return scheduleCalculator.getSchedules(
				objectStorage.getScheduledLoadConsumerParameters(),
				objectStorage.getRandomLoadConsumerParameters(),
				objectStorage.getPowerStationParameters());
	}

	private void sendSchedules(List<PowerStationGenerationSchedule> schedules) {
		for (PowerStationGenerationSchedule schedule : schedules) {
			connector.send(schedule);
		}
	}

	public void register(RandomLoadConsumerParameters parameters) {
		objectStorage.register(parameters);
	}

	public void register(ScheduledLoadConsumerParameters parameters) {
		objectStorage.register(parameters);
	}

	public void register(PowerStationParameters parameters) {
		objectStorage.register(parameters);
	}

	public void acceptConsumerStates(List<ConsumerState> states) {
		saver.saveConsumerStates(states);
	}

	public void acceptPowerStationStates(List<PowerStationState> states) {
		saver.savePowerStationStates(states);
	}
}