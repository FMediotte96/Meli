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
import com.mercadolibre.api.model.SatelliteSplitRequest;
import com.mercadolibre.api.model.TopSecretResponse;
import com.mercadolibre.api.service.ITopSecretService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/topsecret_split")
public class TopSecretSplitRestController {
	
	@Autowired
	private ITopSecretService topSecretService;
	
	@Operation(summary = "Set the distance and the message receive from the imperialship")
	@PostMapping(path = "/{satellite_name}")
	public ResponseEntity<Satellite> setDistanceAndMessage(@PathVariable("satellite_name") String name,
			@RequestBody SatelliteSplitRequest satelliteSplitRequest) {
		SatelliteRequest satelliteRequest = new SatelliteRequest(name, satelliteSplitRequest.getDistance(), satelliteSplitRequest.getMessage());
		Satellite infoSatellite = topSecretService.setDistanceAndMessage(satelliteRequest);
		return ResponseEntity.ok(infoSatellite);
		
	}
	
	@Operation(summary = "Decode the imperialship message and its location if it has the complete information to do it")
	@GetMapping
	public ResponseEntity<TopSecretResponse> decodeAndLocalizeSplit() {
		TopSecretResponse response = topSecretService.decodeAndLocalizeSplit();
		if(response.getMessage() == null || response.getPosition() == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(response);
	}
}
