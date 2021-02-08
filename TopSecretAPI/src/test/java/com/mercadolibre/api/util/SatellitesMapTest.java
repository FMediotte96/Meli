package com.mercadolibre.api.util;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.mercadolibre.api.enums.SatellitesEnum;
import com.mercadolibre.api.model.Position;
import com.mercadolibre.api.model.Satellite;
import com.mercadolibre.api.model.SatelliteRequest;

public class SatellitesMapTest {

	private static SatellitesMap satellitesMap;
	private static final String KENOBI = SatellitesEnum.KENOBI.getName();
	private static final String SKYWALKER = SatellitesEnum.SKYWALKER.getName();
	private static final String SATO = SatellitesEnum.SATO.getName();


	@BeforeEach
	public void getInstance() {
		satellitesMap = SatellitesMap.getInstance();
	}

	@Test
	public void getSatellite() {
		Satellite expected = new Satellite(SKYWALKER, new Position(100, -100));
		Satellite s1 = satellitesMap.getSatellite(SKYWALKER);

		Assertions.assertEquals(expected, s1);
	}

	@Test
	public void getSatellites() {
		Satellite kenobi = new Satellite(KENOBI, new Position(-500, -200));
		Satellite skywalker = new Satellite(SKYWALKER, new Position(100, -100));
		Satellite sato = new Satellite(SATO, new Position(500, 100));

		Map<String, Satellite> satellites = new LinkedHashMap<String, Satellite>();

		satellites.put(kenobi.getName(), kenobi);
		satellites.put(skywalker.getName(), skywalker);
		satellites.put(sato.getName(), sato);

		Map<String, Satellite> result = satellitesMap.getSatellites();

		Assertions.assertEquals(satellites.get(KENOBI), result.get("kenobi"));
		Assertions.assertEquals(satellites.get(SKYWALKER), result.get("skywalker"));
		Assertions.assertEquals(satellites.get(SATO), result.get("sato"));
	}

	@Test
	public void completeDataForSatellite() {
		SatelliteRequest request = new SatelliteRequest(SATO, 100, new String[] { "este", "es" });
		satellitesMap.completeDataForSatellite(request);

		Satellite sato = satellitesMap.getSatellite(SATO);

		Assertions.assertEquals(request.getDistance(), sato.getDistanceFromShip());
		Assertions.assertEquals(request.getMessage(), sato.getReceiptMessage());
	}
	
	@Test
	public void updateDataSatellites() {
		SatelliteRequest requestKenobi = new SatelliteRequest(KENOBI, 100, new String[] { "este", "es" });
		SatelliteRequest requestSkywalker = new SatelliteRequest(SKYWALKER, 100, new String[] { "este", "es" });
		SatelliteRequest requestSato = new SatelliteRequest(SATO, 100, new String[] { "este", "es" });
		
		SatelliteRequest[] satellites = {requestKenobi,requestSkywalker,requestSato};
		satellitesMap.updateDataSatellites(satellites);
		
		Map<String, Satellite> result = satellitesMap.getSatellites();
		
		Assertions.assertEquals(requestKenobi.getDistance(), result.get(KENOBI).getDistanceFromShip());
		Assertions.assertEquals(requestKenobi.getMessage(), result.get(KENOBI).getReceiptMessage());
		Assertions.assertEquals(requestSkywalker.getDistance(), result.get(SKYWALKER).getDistanceFromShip());
		Assertions.assertEquals(requestSkywalker.getMessage(), result.get(SKYWALKER).getReceiptMessage());
		Assertions.assertEquals(requestSato.getDistance(), result.get(SATO).getDistanceFromShip());
		Assertions.assertEquals(requestSato.getMessage(), result.get(SATO).getReceiptMessage());
	}
	
	@Test
	public void clearInfoData() {
		SatelliteRequest requestKenobi = new SatelliteRequest(KENOBI, 100, new String[] { "este", "es" });
		satellitesMap.completeDataForSatellite(requestKenobi);
		
		satellitesMap.clearInfoData();
		
		Satellite kenobi = satellitesMap.getSatellite(KENOBI);
		
		Assertions.assertNull(kenobi.getDistanceFromShip());
		Assertions.assertNull(kenobi.getReceiptMessage());
	}
	
}
