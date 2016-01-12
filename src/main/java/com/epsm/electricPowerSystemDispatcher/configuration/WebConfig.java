package com.epsm.electricPowerSystemDispatcher.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan("com.epsm.electricPowerSystemDispatcher.controller")
public class WebConfig extends WebMvcConfigurerAdapter{
	
}
