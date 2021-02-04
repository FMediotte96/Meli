package com.mercadolibre.app.util;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.mercadolibre.app.model.Position;
import com.mercadolibre.app.model.Satellite;
import com.mercadolibre.app.model.SatelliteRequest;

public class SatellitesMap {
	
	private static SatellitesMap instance;
	private HashMap<String, Satellite> satellites;

	public static SatellitesMap getInstance() {
		if(instance == null) {
			instance = new SatellitesMap();
		}
		return instance;
	}

	private SatellitesMap() {
		Satellite kenobi = new Satellite("Kenobi", new Position(0,0));
		Satellite skywalker = new Satellite("Skywalker", new Position(10,10));
		Satellite sato = new Satellite("Sato", new Position(0,20));
		
		satellites = new LinkedHashMap<String, Satellite>();
		
		satellites.put(kenobi.getName(), kenobi);
		satellites.put(skywalker.getName(), skywalker);
		satellites.put(sato.getName(), sato);
	}
	
	public Satellite getSatellite(String name) {
		return satellites.get(name);
	}
	
	public HashMap<String, Satellite> getSatellites() {
		return satellites;
	}
	
	public void completeDataForSatellite(SatelliteRequest s) {
		Satellite updateSatellite = satellites.get(s.getName());
		updateSatellite.setReceiptMessage(s.getMessage());
		updateSatellite.setDistanceFromShip(s.getDistance());
		satellites.put(updateSatellite.getName(), updateSatellite);
	}
	
	public void clearInfoData() {
		satellites.values().forEach(s -> {
			s.setDistanceFromShip(0.0);
			s.setReceiptMessage(null);
		});
	}
	
}
