package com.epsm.electricPowerSystemDispatcher.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.epsm.electricPowerSystemDispatcher.model.domain.SavedConsumerState;
import com.epsm.electricPowerSystemDispatcher.service.PowerObjectService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(MockitoJUnitRunner.class)
public class ConsumerControllerTest {
	private MockMvc mockMvc;
	private String objectInJsonString;
	
	@InjectMocks
	private ConsumerController controller;
	
	@Mock
	private PowerObjectService service;
	
	@Before
	public void initialize(){
		mockMvc = standaloneSetup(controller).build();
	}
	
	@Test
	public void testRegisterigConsumer() throws Exception{
		mockMvc.perform(
				post("/api/consumer/register/168645468"))
				.andExpect(status().isOk());
	}
	
	@Test
	public void testAcceptConsumerState() throws Exception {
		prepareJsonObject();
		
		mockMvc.perform(
				post("/api/consumer/acceptstate")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectInJsonString))
				.andExpect(status().isOk());
	}
	
	private void prepareJsonObject() throws JsonProcessingException{
		SavedConsumerState state = new SavedConsumerState();
		ObjectMapper mapper = new ObjectMapper();

		objectInJsonString = mapper.writeValueAsString(state);
	}
}
