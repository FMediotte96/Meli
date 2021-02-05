package com.mercadolibre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.api.model.Satellite;
import com.mercadolibre.api.model.SatelliteRequest;
import com.mercadolibre.api.model.TopSecretResponse;
import com.mercadolibre.api.service.ITopSecretService;

@RestController
@RequestMapping("/topsecret_split")
public class TopSecretSplitRestController {
	
	@Autowired
	private ITopSecretService topSecretService;
	
	@PostMapping(path = "/{satellite_name}")
	public ResponseEntity<Satellite> setDistanceAndMessage(@PathVariable("satellite_name") String name,
			@RequestBody SatelliteRequest satelliteRequest) {
		satelliteRequest.setName(name);
		Satellite infoSatellite = topSecretService.setDistanceAndMessage(satelliteRequest);
		return ResponseEntity.ok(infoSatellite);
		
	}
	
	@GetMapping
	public ResponseEntity<TopSecretResponse> decodeAndLocalizeSplit() {
		TopSecretResponse response = topSecretService.decodeAndLocalizeSplit();
		if(response.getMessage() == null) {
			return ResponseEntity.notFound().build();
		}
		if(response.getPosition() == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(response);
	}
}
