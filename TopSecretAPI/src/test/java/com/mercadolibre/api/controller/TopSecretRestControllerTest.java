package com.mercadolibre.api.controller;


import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeAll;
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
import com.mercadolibre.api.model.SatelliteRequest;
import com.mercadolibre.api.model.TopSecretRequest;
import com.mercadolibre.api.model.TopSecretResponse;
import com.mercadolibre.api.service.ITopSecretService;

@WebMvcTest(TopSecretRestController.class)
public class TopSecretRestControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private ITopSecretService service;
	
	private static final String KENOBI = SatellitesEnum.KENOBI.getName();
	private static final String SKYWALKER = SatellitesEnum.SKYWALKER.getName();
	private static final String SATO = SatellitesEnum.SATO.getName();
	private static SatelliteRequest requestKenobi;
	private static SatelliteRequest requestSkywalker;
	private static SatelliteRequest requestSato;
	private static SatelliteRequest[] satellites;
	
	@BeforeAll
	public static void setUp() {
		requestKenobi = new SatelliteRequest(KENOBI, 100, new String[] { "este","","","mensaje",""});
		requestSkywalker = new SatelliteRequest(SKYWALKER, 100, new String[] { "","es","","","secreto" });
		requestSato = new SatelliteRequest(SATO, 100, new String[] { "este","","un","","" });
		
		satellites = new SatelliteRequest[3];
		satellites[0] = requestKenobi;
		satellites[1] = requestSkywalker;
		satellites[2] = requestSato;

	}
	
	@Test
	public void decodeAndLocalizeOk() throws Exception {
		Position p = new Position(3,0);
		String message = "este es un mensaje secreto";
		
		TopSecretResponse response = new TopSecretResponse(p, message);
		when(service.decodeAndLocalize(ArgumentMatchers.any())).thenReturn(response);
		
		TopSecretRequest topSecretRequest = new TopSecretRequest();
		topSecretRequest.setSatellites(satellites);
		
		mockMvc.perform(post("/topsecret")
				.content(asJsonString(topSecretRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.position.x", is(p.getX())))
				.andExpect(jsonPath("$.position.y", is(p.getY())))
				.andExpect(jsonPath("$.message", is(message)));
	}
	
	@Test
	public void decodeAndLocalizeNotFoundMessage() throws Exception {
		Position p = new Position(3,0);
		String message = null;
		
		TopSecretResponse response = new TopSecretResponse(p, message);
		when(service.decodeAndLocalize(ArgumentMatchers.any())).thenReturn(response);
		
		TopSecretRequest topSecretRequest = new TopSecretRequest();
		topSecretRequest.setSatellites(satellites);
		
		mockMvc.perform(post("/topsecret")
				.content(asJsonString(topSecretRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}
	
	@Test
	public void decodeAndLocalizeNotFoundPosition() throws Exception {
		Position p = null;
		String message = "este es un mensaje secreto";
		
		TopSecretResponse response = new TopSecretResponse(p, message);
		when(service.decodeAndLocalize(ArgumentMatchers.any())).thenReturn(response);
		
		TopSecretRequest topSecretRequest = new TopSecretRequest();
		topSecretRequest.setSatellites(satellites);
		
		mockMvc.perform(post("/topsecret")
				.content(asJsonString(topSecretRequest))
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
		
	}
	
	public static String asJsonString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}

}
