package com.epsm.epsdcore.model;

import com.epsm.epsmcore.model.consumption.RandomLoadConsumerParameters;
import com.epsm.epsmcore.model.consumption.ScheduledLoadConsumerParameters;
import com.epsm.epsmcore.model.generation.PowerStationParameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class PowerObjectStorage {

	private Map<Long, ScheduledLoadConsumerParameters> scheduledLoadConsumerParameters = new ConcurrentHashMap<>();
	private Map<Long, RandomLoadConsumerParameters> randomLoadConsumerParameters = new ConcurrentHashMap<>();
	private Map<Long, PowerStationParameters> powerStationParameters = new ConcurrentHashMap<>();
	private Logger logger = LoggerFactory.getLogger(PowerObjectStorage.class);

	public void register(ScheduledLoadConsumerParameters parameters) {
		logger.info("registered: {}.", parameters);
		scheduledLoadConsumerParameters.put(parameters.getPowerObjectId(), parameters);
	}

	public void register(RandomLoadConsumerParameters parameters) {
		logger.info("registered: {}.", parameters);
		randomLoadConsumerParameters.put(parameters.getPowerObjectId(), parameters);
	}

	public void register(PowerStationParameters parameters) {
		logger.info("registered: {}.", parameters);
		powerStationParameters.put(parameters.getPowerObjectId(), parameters);
	}

	public Collection<ScheduledLoadConsumerParameters> getScheduledLoadConsumerParameters() {
		return scheduledLoadConsumerParameters.values();
	}

	public Collection<RandomLoadConsumerParameters> getRandomLoadConsumerParameters() {
		return randomLoadConsumerParameters.values();
	}

	public Collection<PowerStationParameters> getPowerStationParameters() {
		return powerStationParameters.values();
	}
}
