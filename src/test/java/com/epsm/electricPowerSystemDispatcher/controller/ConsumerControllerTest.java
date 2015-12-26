package com.epsm.electricPowerSystemDispatcher.controller;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epsm.electricPowerSystemDispatcher.model.domain.ConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.PowerObjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class ConsumerControllerTest {
	
	private String jsonObject;
	
	@InjectMocks
	private ConsumerController controller;
	
	@Mock
	private PowerObjectService service;
	
	@Test
	public void testRegisterigPowerStation() throws Exception{
		String url = "/api/consumer/register/168645468";
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		mockMvc.perform(
				post(url))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAcceptPowerStationState() throws Exception {
		MockMvc mockMvc = standaloneSetup(controller).build();
		
		prepareJsonObject();
		mockMvc.perform(
				post("/api/consumer/acceptstate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(jsonObject))
				.andExpect(status().isOk());
	}
	
	private void prepareJsonObject() throws JsonProcessingException{
		ConsumerState state = new ConsumerState();
		ObjectMapper mapper = new ObjectMapper();

		jsonObject = mapper.writeValueAsString(state);
	}
}
