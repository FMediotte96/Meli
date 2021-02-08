package com.mercadolibre.api.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.mercadolibre.api.model.Position;
import com.mercadolibre.api.model.Satellite;
import com.mercadolibre.api.util.SatellitesMap;

@Service
public class PositionServiceImpl implements IPositionService {
	
	private static SatellitesMap satellitesMap = SatellitesMap.getInstance();

	@Override
	public Satellite changePositionByName(String name, Position position) {
		return satellitesMap.updatePosition(name, position);
	}

	@Override
	public Map<String, Satellite> resetDefaultPositionValue() {
		satellitesMap.restartPositionByDefault();
		satellitesMap = SatellitesMap.getInstance();
		return satellitesMap.getSatellites();
	}

	@Override
	public Map<String, Satellite> getAllSatellitesPosition() {
		return satellitesMap.getSatellites();
	}
	
}
