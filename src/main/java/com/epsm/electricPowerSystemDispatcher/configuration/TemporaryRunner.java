package com.epsm.electricPowerSystemDispatcher.configuration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.epsm.electricPowerSystemDispatcher.model.DispatcherImpl;

public class TemporaryRunner {
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.scan("com.epsm.electricPowerSystemDispatcher");
		context.refresh();
		
		DispatcherImpl impl = context.getBean(DispatcherImpl.class);
	}
}
