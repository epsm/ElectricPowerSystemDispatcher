package com.epsm.electricPowerSystemDispatcher.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.ConsumerParametersStub;
import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.ConsumerState;
import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.Dispatcher;
import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.PowerStationParameters;
import com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel.PowerStationState;

@Transactional
@Service
public class IncomingMessageServiceImpl implements IncomingMessageService{

	@Autowired
	private Dispatcher dispatcher;
	
	@Override
	public void establishConnectionWithConsumer(ConsumerParametersStub parameters) {
		dispatcher.establishConnection(parameters);
	}

	@Override
	public void acceptConsumerState(ConsumerState state) {
		dispatcher.acceptState(state);
	}

	@Override
	public void establishConnectionWithPowerStation(PowerStationParameters parameters) {
		dispatcher.establishConnection(parameters);
	}

	@Override
	public void acceptPowerStationState(PowerStationState state) {
		dispatcher.acceptState(state);
	}
}
