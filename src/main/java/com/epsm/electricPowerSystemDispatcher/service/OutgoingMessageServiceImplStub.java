package com.epsm.electricPowerSystemDispatcher.service;

import org.springframework.stereotype.Service;

import com.epsm.electricPowerSystemModel.model.dispatch.Command;

@Service
public class OutgoingMessageServiceImplStub implements OutgoingMessageService{

	@Override
	public void sendCommand(Command command) {

	}
}
