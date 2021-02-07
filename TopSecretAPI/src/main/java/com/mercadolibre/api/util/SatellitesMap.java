package com.mercadolibre.api.util;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import com.mercadolibre.api.enums.SatellitesEnum;
import com.mercadolibre.api.exceptions.EntityNotFoundException;
import com.mercadolibre.api.model.Position;
import com.mercadolibre.api.model.Satellite;
import com.mercadolibre.api.model.SatelliteRequest;

public class SatellitesMap {
	
	private static SatellitesMap instance;
	private Map<String, Satellite> satellites;

	public static SatellitesMap getInstance() {
		if(instance == null) {
			instance = new SatellitesMap();
		}
		return instance;
	}

	private SatellitesMap() {
		Satellite kenobi = new Satellite(SatellitesEnum.KENOBI.getName(), new Position(-500,-200));
		Satellite skywalker = new Satellite(SatellitesEnum.SKYWALKER.getName(), new Position(100,-100));
		Satellite sato = new Satellite(SatellitesEnum.SATO.getName(), new Position(500,100));
		
		satellites = new LinkedHashMap<String, Satellite>();
		
		satellites.put(kenobi.getName(), kenobi);
		satellites.put(skywalker.getName(), skywalker);
		satellites.put(sato.getName(), sato);
	}
	
	public Satellite getSatellite(String name) {
		return satellites.get(name);
	}
	
	public Map<String, Satellite> getSatellites() {
		return satellites;
	}
	
	public void updateDataSatellites(SatelliteRequest[] satellites) {
		//Guardo la información recibida en mis satelites rebeldes
		Arrays.stream(satellites).parallel().forEach(s -> completeDataForSatellite(s));
	}
	
	public void completeDataForSatellite(SatelliteRequest s) {
		Satellite updateSatellite = satellites.get(s.getName().toLowerCase());
		try {
			updateSatellite.setReceiptMessage(s.getMessage());
			updateSatellite.setDistanceFromShip(s.getDistance());
			satellites.put(updateSatellite.getName(), updateSatellite);
		} catch (NullPointerException e) {
			throw new EntityNotFoundException("No existe el satelite: " + s.getName() 
				+ " enviado como parametro");
		}
		
	}
	
	public void clearInfoData() {
		satellites.values().parallelStream().forEach(s -> {
			s.setDistanceFromShip(null);
			s.setReceiptMessage(null);
		});
	}
	
}
