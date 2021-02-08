package com.mercadolibre.api.service;

import java.util.Map;

import com.mercadolibre.api.model.Position;
import com.mercadolibre.api.model.Satellite;

public interface IPositionService {
	
	Satellite changePositionByName(String name, Position position);
	Map<String, Satellite> resetDefaultPositionValue();
	Map<String, Satellite> getAllSatellitesPosition();
}
