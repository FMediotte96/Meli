package com.mercadolibre.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.app.model.TopSecretRequest;
import com.mercadolibre.app.model.TopSecretResponse;
import com.mercadolibre.app.service.ITopSecretService;

@RestController
@RequestMapping("/topsecret")
public class TopSecretRestController {
	
	@Autowired
	private ITopSecretService topSecretService;
	
	@PostMapping
	public ResponseEntity<TopSecretResponse> decodeAndLocalize(@RequestBody TopSecretRequest topSecretRequest) {
		TopSecretResponse topSecret = topSecretService.decodeAndLocalize(topSecretRequest.getSatellites());
		if(topSecret.getMessage() == null) {
			return ResponseEntity.notFound().build();
		}
		if(topSecret.getPosition() == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(topSecret);
	}
	
}
