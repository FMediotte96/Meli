package com.mercadolibre.api.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.mercadolibre.api.model.Position;
import com.mercadolibre.api.model.Satellite;
import com.mercadolibre.api.util.SatellitesMap;

@Service
public class PositionServiceImpl implements IPositionService {
	
	@Override
	public Satellite changePositionByName(String name, Position position) {
		return SatellitesMap.getInstance().updatePosition(name, position);
	}

	@Override
	public Map<String, Satellite> resetDefaultPositionValue() {
		SatellitesMap.getInstance().restartPositionByDefault();
		return SatellitesMap.getInstance().getSatellites();
	}

	@Override
	public Map<String, Satellite> getAllSatellitesPosition() {
		return SatellitesMap.getInstance().getSatellites();
	}
	
}
