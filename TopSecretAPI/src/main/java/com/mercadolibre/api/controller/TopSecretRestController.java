package com.mercadolibre.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.api.model.TopSecretRequest;
import com.mercadolibre.api.model.TopSecretResponse;
import com.mercadolibre.api.service.ITopSecretService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/topsecret")
public class TopSecretRestController {
	
	@Autowired
	private ITopSecretService topSecretService;
	
	@Operation(summary = "Decode the imperialship message and its location")
	@PostMapping
	public ResponseEntity<TopSecretResponse> decodeAndLocalize(@RequestBody TopSecretRequest topSecretRequest) {
		TopSecretResponse topSecret = topSecretService.decodeAndLocalize(topSecretRequest.getSatellites());
		if(topSecret.getMessage() == null || topSecret.getPosition() == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(topSecret);
	}
	
}
