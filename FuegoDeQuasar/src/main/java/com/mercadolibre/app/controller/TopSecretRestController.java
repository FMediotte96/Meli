package com.mercadolibre.app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<TopSecretResponse> decodeAndLocalize(@RequestBody TopSecretRequest satellitesRequest) {
		TopSecretResponse topSecret = topSecretService.decodeAndLocalize(satellitesRequest.getSatellites());
		if(topSecret == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(topSecret, HttpStatus.OK);
	}

}
