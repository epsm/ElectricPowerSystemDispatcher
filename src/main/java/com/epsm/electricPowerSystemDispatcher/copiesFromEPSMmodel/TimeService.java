package com.epsm.electricPowerSystemDispatcher.copiesFromEPSMmodel;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class TimeService {
	public LocalDateTime getCurrentTime(){
		return LocalDateTime.now();
	}
}