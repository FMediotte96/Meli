package com.mercadolibre.api.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mercadolibre.api.enums.SatellitesEnum;
import com.mercadolibre.api.model.Position;
import com.mercadolibre.api.model.Satellite;
import com.mercadolibre.api.model.SatelliteRequest;
import com.mercadolibre.api.model.TopSecretResponse;
import com.mercadolibre.api.service.ITopSecretService;

@WebMvcTest(TopSecretSplitRestController.class)
public class TopSecretSplitRestControllerTest {

	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ITopSecretService service;
	
	private static final String KENOBI = SatellitesEnum.KENOBI.getName();
	
	@Test
	public void setDistanceAndMessage() throws Exception {
		
        String[] messageCode = new String[] { "este","","","mensaje",""};
		SatelliteRequest requestKenobi = new SatelliteRequest(KENOBI,100,messageCode);
		
		Position p = new Position(3,0);
		Satellite satellite = new Satellite(KENOBI, p);
		satellite.setDistanceFromShip(100.0);
		satellite.setReceiptMessage(messageCode);
		
		when(service.setDistanceAndMessage(ArgumentMatchers.any())).thenReturn(satellite);
		
		mockMvc.perform(post("/topsecret_split/{satellite_name}",KENOBI)
				.content(asJsonString(requestKenobi))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name",is(KENOBI)));
	}
	
	@Test
	public void decodeAndLocalizeSplitOk() throws Exception {
		
		Position p = new Position(3,0);
		String message = "este es un mensaje secreto";
		
		TopSecretResponse response = new TopSecretResponse(p, message);
		when(service.decodeAndLocalizeSplit()).thenReturn(response);
		
		mockMvc.perform(get("/topsecret_split"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.position.x", is(p.getX())))
				.andExpect(jsonPath("$.position.y", is(p.getY())))
				.andExpect(jsonPath("$.message", is(message)));
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
