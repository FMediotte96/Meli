package com.mercadolibre.api.controller;


import static org.mockito.Mockito.when;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.mercadolibre.api.enums.SatellitesEnum;
import com.mercadolibre.api.model.Position;
import com.mercadolibre.api.model.SatelliteRequest;
import com.mercadolibre.api.model.TopSecretRequest;
import com.mercadolibre.api.model.TopSecretResponse;
import com.mercadolibre.api.service.ITopSecretService;

@ExtendWith(MockitoExtension.class)
public class TopSecretRestControllerTest {
	
	@InjectMocks
	TopSecretRestController restController;
	
	@Mock
	private ITopSecretService service;
	
	private static final String KENOBI = SatellitesEnum.KENOBI.getName();
	private static final String SKYWALKER = SatellitesEnum.SKYWALKER.getName();
	private static final String SATO = SatellitesEnum.SATO.getName();
	
	@Test
	public void decodeAndLocalizeOk() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		SatelliteRequest requestKenobi = new SatelliteRequest(KENOBI, 100, new String[] { "este","","","mensaje",""});
		SatelliteRequest requestSkywalker = new SatelliteRequest(SKYWALKER, 100, new String[] { "","es","","","secreto" });
		SatelliteRequest requestSato = new SatelliteRequest(SATO, 100, new String[] { "este","","un","","" });
		
		SatelliteRequest[] satellites = {requestKenobi,requestSkywalker,requestSato};
		
		Position p = new Position(3,0);
		String message = "este es un mensaje secreto";
		
		TopSecretResponse response = new TopSecretResponse(p, message);
		when(service.decodeAndLocalize(satellites)).thenReturn(response);
		
		TopSecretRequest topSecretRequest = new TopSecretRequest();
		topSecretRequest.setSatellites(satellites);
		
		ResponseEntity<TopSecretResponse> responseEntity = restController.decodeAndLocalize(topSecretRequest);
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
		Assertions.assertThat(responseEntity.getBody().getPosition()).isEqualTo(p);
		Assertions.assertThat(responseEntity.getBody().getMessage()).isEqualTo(message);
	}
	
	@Test
	public void decodeAndLocalizeNotFoundMessage() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		SatelliteRequest requestKenobi = new SatelliteRequest(KENOBI, 100, new String[] { "este","","","mensaje",""});
		SatelliteRequest requestSkywalker = new SatelliteRequest(SKYWALKER, 100, new String[] { "","es","","","secreto" });
		SatelliteRequest requestSato = new SatelliteRequest(SATO, 100, new String[] { "este","","un","","" });
		
		SatelliteRequest[] satellites = {requestKenobi,requestSkywalker,requestSato};
		
		Position p = new Position(3,0);
		String message = null;
		
		TopSecretResponse response = new TopSecretResponse(p, message);
		when(service.decodeAndLocalize(satellites)).thenReturn(response);
		
		TopSecretRequest topSecretRequest = new TopSecretRequest();
		topSecretRequest.setSatellites(satellites);
		
		ResponseEntity<TopSecretResponse> responseEntity = restController.decodeAndLocalize(topSecretRequest);
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
	}
	
	@Test
	public void decodeAndLocalizeNotFoundPosition() {
		MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		SatelliteRequest requestKenobi = new SatelliteRequest(KENOBI, 100, new String[] { "este","","","mensaje",""});
		SatelliteRequest requestSkywalker = new SatelliteRequest(SKYWALKER, 100, new String[] { "","es","","","secreto" });
		SatelliteRequest requestSato = new SatelliteRequest(SATO, 100, new String[] { "este","","un","","" });
		
		SatelliteRequest[] satellites = {requestKenobi,requestSkywalker,requestSato};
		
		Position p = null;
		String message = "este es un mensaje secreto";
		
		TopSecretResponse response = new TopSecretResponse(p, message);
		when(service.decodeAndLocalize(satellites)).thenReturn(response);
		
		TopSecretRequest topSecretRequest = new TopSecretRequest();
		topSecretRequest.setSatellites(satellites);
		
		ResponseEntity<TopSecretResponse> responseEntity = restController.decodeAndLocalize(topSecretRequest);
		Assertions.assertThat(responseEntity.getStatusCodeValue()).isEqualTo(404);
	}


}
