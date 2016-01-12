package com.epsm.electricPowerSystemDispatcher.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import com.epsm.electricPowerSystemModel.client.AbstractClient;
import com.epsm.electricPowerSystemModel.model.generalModel.TimeService;

@Configuration
@ComponentScan("com.epsm.electricPowerSystemDispatcher")
@Import({AbstractClient.class, TimeService.class})			
public class ApplicationConfig{
	
	@Bean
    public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		PropertySourcesPlaceholderConfigurer configurer 
				= new PropertySourcesPlaceholderConfigurer();
		configurer.setLocation(new ClassPathResource("application.properties"));
        
        return configurer;
    }
}