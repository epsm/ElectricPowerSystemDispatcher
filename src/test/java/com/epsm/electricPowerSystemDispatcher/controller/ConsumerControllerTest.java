package com.epsm.electricPowerSystemDispatcher.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epsm.electricPowerSystemDispatcher.service.IncomingMessageService;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumerParametersStub;
import com.epsm.electricPowerSystemModel.model.consumption.ConsumerState;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class ConsumerControllerTest {
	private MockMvc mockMvc;
	private ObjectMapper mapper;
	private String objectInJsonString;
	private Object objectToSerialize;
	
	@InjectMocks
	private ConsumerController controller;
	
	@Mock
	private IncomingMessageService service;
	
	@Before
	public void initialize(){
		mockMvc = standaloneSetup(controller).build();
		mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
	}
	
	@Test
	public void registerigConsumerMethodAceptsConsumerParameters() throws Exception{
		prepareParemetersAsJSONString();
		
		mockMvc.perform(
				post("/api/consumer/esatblishconnection")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectInJsonString))
				.andExpect(status().isOk());
	}
	
	private void prepareParemetersAsJSONString() throws JsonProcessingException{
		objectToSerialize = new ConsumerParametersStub(0, LocalDateTime.MIN, LocalTime.MIN);
		objectInJsonString = mapper.writeValueAsString(objectToSerialize);
	}
	
	@Test
	public void acceptConsumerStateMethodAcceptsConsumerState() throws Exception {
		prepareStateAsJSONString();
		
		mockMvc.perform(
				post("/api/consumer/acceptstate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectInJsonString))
				.andExpect(status().isOk());
	}
	
	private void prepareStateAsJSONString() throws JsonProcessingException{
		objectToSerialize = new ConsumerState(0, LocalDateTime.MIN, LocalTime.MIN, 20);
		objectInJsonString = mapper.writeValueAsString(objectToSerialize);
	}
}
