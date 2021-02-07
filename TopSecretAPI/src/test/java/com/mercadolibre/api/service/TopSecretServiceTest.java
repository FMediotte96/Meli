package com.mercadolibre.api.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.mercadolibre.api.enums.SatellitesEnum;
import com.mercadolibre.api.model.Position;
import com.mercadolibre.api.model.Satellite;
import com.mercadolibre.api.model.SatelliteRequest;
import com.mercadolibre.api.model.TopSecretResponse;
import com.mercadolibre.api.util.SatellitesMap;

public class TopSecretServiceTest {
	
	private ITopSecretService service = new TopSecretServiceImpl();
	
	private static final String KENOBI = SatellitesEnum.KENOBI.getName();
	private static final String SKYWALKER = SatellitesEnum.SKYWALKER.getName();
	private static final String SATO = SatellitesEnum.SATO.getName();
	
	@Test
	public void decodeAndLocalize() {
		
		SatelliteRequest requestKenobi = new SatelliteRequest(KENOBI, Math.sqrt(250000), new String[] { "este","","","mensaje",""});
		SatelliteRequest requestSkywalker = new SatelliteRequest(SKYWALKER, Math.sqrt(80000), new String[] { "","es","","","secreto" });
		SatelliteRequest requestSato = new SatelliteRequest(SATO, Math.sqrt(360000), new String[] { "este","","un","","" });
		SatelliteRequest[] satellites = {requestKenobi,requestSkywalker,requestSato};
		
		Position p = new Position(-100,100);
		String message = "este es un mensaje secreto";
		TopSecretResponse expected = new TopSecretResponse(p, message);

		TopSecretResponse result = service.decodeAndLocalize(satellites);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void decodeAndLocalizeMessageNull() {
		
		SatelliteRequest requestKenobi = new SatelliteRequest(KENOBI, Math.sqrt(250000), new String[] { "este","","","mensaje",""});
		SatelliteRequest requestSkywalker = new SatelliteRequest(SKYWALKER, Math.sqrt(80000), new String[] { "","es","","",""});
		SatelliteRequest requestSato = new SatelliteRequest(SATO, Math.sqrt(360000), new String[] { "este","","un","","" });
		SatelliteRequest[] satellites = {requestKenobi,requestSkywalker,requestSato};
		
		TopSecretResponse result = service.decodeAndLocalize(satellites);
		
		Assertions.assertNull(result.getMessage());
	}
	
	@Test
	public void decodeAndLocalizePositionNull() {
		
		SatelliteRequest requestKenobi = new SatelliteRequest(KENOBI, Math.sqrt(10), new String[] { "este","","","mensaje",""});
		SatelliteRequest requestSkywalker = new SatelliteRequest(SKYWALKER, Math.sqrt(10), new String[] { "","es","","","secreto"});
		SatelliteRequest requestSato = new SatelliteRequest(SATO, Math.sqrt(10), new String[] { "este","","un","","" });
		SatelliteRequest[] satellites = {requestKenobi,requestSkywalker,requestSato};
		
		TopSecretResponse result = service.decodeAndLocalize(satellites);
		
		Assertions.assertNull(result.getPosition());
	}
	
	@Test
	public void setDistanceAndMessageKenobi() {

		SatelliteRequest requestKenobi = new SatelliteRequest(KENOBI, Math.sqrt(250000), 
				new String[] { "este","","","mensaje",""});
		
		Satellite expected = new Satellite(KENOBI, new Position(-500,-200));
		expected.setDistanceFromShip(Math.sqrt(250000));
		expected.setReceiptMessage(new String[] { "este","","","mensaje",""});
		
		Satellite result = service.setDistanceAndMessage(requestKenobi);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void setDistanceAndMessageSkywalker() {

		SatelliteRequest requestKenobi = new SatelliteRequest(SKYWALKER, Math.sqrt(80000), 
				new String[] { "","es","","","secreto" });
		
		Satellite expected = new Satellite(SKYWALKER, new Position(100,-100));
		expected.setDistanceFromShip(Math.sqrt(80000));
		expected.setReceiptMessage(new String[] { "","es","","","secreto" });
		
		Satellite result = service.setDistanceAndMessage(requestKenobi);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void setDistanceAndMessageSato() {

		SatelliteRequest requestSato = new SatelliteRequest(SATO, Math.sqrt(360000), 
				new String[] { "este","","un","","" });
		
		Satellite expected = new Satellite(SATO, new Position(500,100));
		expected.setDistanceFromShip(Math.sqrt(360000));
		expected.setReceiptMessage(new String[] { "este","","un","","" });
		
		Satellite result = service.setDistanceAndMessage(requestSato);
		
		Assertions.assertEquals(expected, result);
	}
	
	@Test
	public void decodeAndLocalizeSplitOk() {
		
		this.setCompleteInfo();
		Position p = new Position(-100,100);
		String message = "este es un mensaje secreto";
		TopSecretResponse expected = new TopSecretResponse(p, message);

		TopSecretResponse result = service.decodeAndLocalizeSplit();
		
		Assertions.assertEquals(expected, result);
		
		SatellitesMap.getInstance().clearInfoData();
	}
	
	@Test
	public void decodeAndLocalizeSplitNotEnoughInfo() {
		
		TopSecretResponse result = service.decodeAndLocalizeSplit();
		Assertions.assertNull(result.getMessage());
		Assertions.assertNull(result.getPosition());
		SatellitesMap.getInstance().clearInfoData();
	}
	
	private void setCompleteInfo() {
		setDistanceAndMessageKenobi();
		setDistanceAndMessageSkywalker();
		setDistanceAndMessageSato();
	}
	
}
