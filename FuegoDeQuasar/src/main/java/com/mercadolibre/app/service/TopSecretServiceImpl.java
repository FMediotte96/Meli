package com.mercadolibre.app.service;

import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Service;

import com.mercadolibre.app.model.Position;
import com.mercadolibre.app.model.Satellite;
import com.mercadolibre.app.model.SatelliteRequest;
import com.mercadolibre.app.model.TopSecretResponse;
import com.mercadolibre.app.util.TrigonometryUtils;
import com.mercadolibre.app.util.SatellitesMap;

@Service
public class TopSecretServiceImpl implements ITopSecretService {
	
	//Instancia singleton de los satelites disponibles de la alianza rebelde
	private static SatellitesMap satellitesMap = SatellitesMap.getInstance();
	
	@Override
	public TopSecretResponse decodeAndLocalize(List<SatelliteRequest> satellitesRequest) {
		
		TopSecretResponse topSecret = new TopSecretResponse();
		satellitesRequest.forEach(s -> satellitesMap.completeDataForSatellite(s));
		
		topSecret = getLocationAndMessage();
		
		satellitesMap.clearInfoData();
		
		return topSecret;
	}
	
	public TopSecretResponse getLocationAndMessage() {
		double[] distances = satellitesMap.getSatellites().values()
										.stream()
										.mapToDouble(i -> i.getDistanceFromShip())
										.toArray();
		Position pos = getLocation(distances);
		
		if(pos == null) {
			return null;
		}
		
		String[] messages = (String[]) satellitesMap.getSatellites().values()
				.stream()
				.map(it -> it.getReceiptMessage())
				.toArray();
		String message = getMessage(messages);
		
		if(message == null){
			return null;
		}
		
		return new TopSecretResponse(pos,message);
	}

	/**
	 * Método que resuelve la posición de la nave imperial dados 3 posiciones
	 * de satelites de los rebeldes. Se utiliza el concepto de trilateración para 
	 * determinar la posición de la nave
	 * @param distances
	 * @return posicion nave imperial
	 */
	public Position getLocation(double[] distances) {
		
		Position[] positions = new Position[3];
		
		//Hago esta iteración por si el orden en que llega la información de los satelites en el request
		//es distinta a Kenobi, Skywalker, Sato y para poder utilizar correctamente el vector distances
		int i = 0;
		for(Satellite s : satellitesMap.getSatellites().values()) {
			positions[i] = s.getPosition();
			i++;
		}

		//Calculo la posición de la nave imperial
		Position result = TrigonometryUtils.trilateracion(
				positions[0], positions[1], positions[2], 
				distances[0], distances[1], distances[2]);
		return result;
	}
		
	/**
	 * Método que descifra el mensaje recibido en las distintas naves
	 * @param messages
	 * @return
	 */
	public String getMessage(String[] ... messages) {
		
		String[] decodeMessage = new String[messages[0].length];
		
		for(int j=0; j < messages[0].length; j++) {
			int i = 0;
			while(i < messages.length) {
				if(Strings.isBlank(messages[j][i])) {
					decodeMessage[j] = messages[j][i];
					break;
				}
				i++;
				if(i == messages.length) {
					return null;
				}
			}
		}
		
		return StringUtils.join(Arrays.asList(decodeMessage), ' ');
	}


	@Override
	public TopSecretResponse decodeAndLocalizeSplit() {
		//TODO
		return null;
	}

	@Override
	public Satellite setDistanceAndMessage(SatelliteRequest satelliteRequest) {
		satellitesMap.completeDataForSatellite(satelliteRequest);
		return satellitesMap.getSatellites().get(satelliteRequest.getName());
	}
	
}
