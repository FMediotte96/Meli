package com.mercadolibre.api.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mercadolibre.api.model.Position;
import com.mercadolibre.api.model.Satellite;
import com.mercadolibre.api.service.IPositionService;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/position")
public class PositionRestController {
	
	Logger log = LoggerFactory.getLogger(PositionRestController.class);
	
	@Autowired
	private IPositionService positionService;
	
	@Operation(summary = "Change Satellite Position")
	@PutMapping("/{satellite_name}")
	public ResponseEntity<Satellite> changeSatellitePosition(@PathVariable("satellite_name") String satelliteName, 
			@RequestBody Position position) {
		Satellite updatedSatellite = positionService.changePositionByName(satelliteName, position);
		if(updatedSatellite == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(updatedSatellite);
		
	}
	
	@Operation(summary = "Restart all Satellites Position")
	@PostMapping("/restart")
	public ResponseEntity<Map<String,Satellite>> restartPositionByDefault() {
		Map<String, Satellite> result = positionService.resetDefaultPositionValue();
		return ResponseEntity.ok(result);
	}
	
	@Operation(summary = "Get all Satellites Position")
	@GetMapping
	public ResponseEntity<Map<String,Satellite>> getAllSatellitesPosition() {
		Map<String, Satellite> result = positionService.getAllSatellitesPosition();
		return ResponseEntity.ok(result);
	}

}
