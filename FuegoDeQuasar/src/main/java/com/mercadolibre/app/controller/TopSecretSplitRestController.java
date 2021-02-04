package com.mercadolibre.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.app.exceptions.NotEnoughInformationException;
import com.mercadolibre.app.exceptions.SatelliteDoesNotExistsException;
import com.mercadolibre.app.model.Satellite;
import com.mercadolibre.app.model.SatelliteRequest;
import com.mercadolibre.app.model.TopSecretResponse;
import com.mercadolibre.app.service.ITopSecretService;

@RestController
@RequestMapping("/topsecret_split")
public class TopSecretSplitRestController {
	
	@Autowired
	private ITopSecretService topSecretService;
	
	@PostMapping(path = "/{satellite_name}")
	public ResponseEntity<Satellite> setDistanceAndMessage(@PathVariable("satellite_name") String name,
			@RequestParam double distance, @RequestParam String[] message) throws SatelliteDoesNotExistsException {
		SatelliteRequest satelliteRequest = new SatelliteRequest(name, distance, message);
		Satellite infoSatellite = topSecretService.setDistanceAndMessage(satelliteRequest);
		return new ResponseEntity<>(infoSatellite,HttpStatus.OK);
		
	}
	
	@GetMapping
	public ResponseEntity<TopSecretResponse> decodeAndLocalizeSplit() throws NotEnoughInformationException {
		TopSecretResponse response = topSecretService.decodeAndLocalizeSplit();
		if(response.getMessage() == null) {
			throw new NotEnoughInformationException("No hay suficiente información");
		}
		if(response.getPosition() == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
}
