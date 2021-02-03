package com.mercadolibre.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.app.model.SatelliteRequest;
import com.mercadolibre.app.model.TopSecretResponse;
import com.mercadolibre.app.service.ITopSecretService;

@RestController
@RequestMapping("/topsecret_split")
public class TopSecretSplitRestController {
	
	@Autowired
	private ITopSecretService topSecretService;
	
	@RequestMapping(path="/{satellite_name}", method = { RequestMethod.POST, RequestMethod.GET })
	public ResponseEntity<TopSecretResponse> decodeAndLocalizeSplitPost(@PathVariable("satellite_name") String name,
			@RequestParam double distance, @RequestParam String[] message) {
		SatelliteRequest satellite = new SatelliteRequest(name, distance, message);
		TopSecretResponse topSecret = topSecretService.decodeAndLocalizeSplit(satellite);
		if(topSecret != null) {
			return new ResponseEntity<>(topSecret, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

}
