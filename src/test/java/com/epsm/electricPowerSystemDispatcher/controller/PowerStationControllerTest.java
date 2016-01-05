package com.epsm.electricPowerSystemDispatcher.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.time.LocalTime;
import java.util.Collections;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epsm.electricPowerSystemDispatcher.service.PowerObjectService;
import com.epsm.electricPowerSystemModel.model.dispatch.Parameters;
import com.epsm.electricPowerSystemModel.model.dispatch.State;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class PowerStationControllerTest {
	private MockMvc mockMvc;
	private ObjectMapper mapper;
	private String objectInJsonString;
	private Object objectToSerialize;
	
	@InjectMocks
	private PowerStationController controller;
	
	@Mock
	private PowerObjectService service;
	
	@Before
	public void initialize(){
		mockMvc = standaloneSetup(controller).build();
		mapper = new ObjectMapper();
		mapper.findAndRegisterModules();
	}
	
	@Test
	public void testRegisterPowerStation() throws Exception {
		prepareParemetersAsJSONString();
		
		mockMvc.perform(
				post("/api/powerstation/register")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectInJsonString))
				.andExpect(status().isOk());
	}
	
	private void prepareParemetersAsJSONString() throws JsonProcessingException{
		objectToSerialize = new Parameters(1, Collections.emptyMap());
		objectInJsonString = mapper.writeValueAsString(objectToSerialize);
	}
	
	@Test
	public void testAcceptPowerStationState() throws Exception {
		prepareStateAsJSONString();
		
		mockMvc.perform(
				post("/api/powerstation/acceptstate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectInJsonString))
				.andExpect(status().isOk());
	}
	
	private void prepareStateAsJSONString() throws JsonProcessingException{
		objectToSerialize = new State(1, LocalTime.NOON, 50, Collections.emptySet());
		objectInJsonString = mapper.writeValueAsString(objectToSerialize);
	}
}
