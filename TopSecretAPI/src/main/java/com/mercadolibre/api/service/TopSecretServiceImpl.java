package com.mercadolibre.api.service;

import java.util.Arrays;
import java.util.Objects;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import com.mercadolibre.api.exceptions.NotEnoughInformationException;
import com.mercadolibre.api.model.Position;
import com.mercadolibre.api.model.Satellite;
import com.mercadolibre.api.model.SatelliteRequest;
import com.mercadolibre.api.model.TopSecretResponse;
import com.mercadolibre.api.util.SatellitesMap;
import com.mercadolibre.api.util.TrigonometryUtils;

@Service
public class TopSecretServiceImpl implements ITopSecretService {
	
	//Instancia singleton de los satelites disponibles de la alianza rebelde
	private static SatellitesMap satellitesMap = SatellitesMap.getInstance();
	
	@Override
	public TopSecretResponse decodeAndLocalize(SatelliteRequest[] satellitesRequest) {
		
		TopSecretResponse topSecret = new TopSecretResponse();
		
		//Actualizo la distancia y el mensaje de c/satelite
		satellitesMap.updateDataSatellites(satellitesRequest);
		
		//Triangulo la ubicación de la nave imperial y obtengo el mensaje cifrado
		String message = getDecodeMessage();
		if(message == null) {
			return topSecret;
		}
		topSecret.setMessage(message);
		
		Position position = getImperialShipLocation();
		topSecret.setPosition(position);
		
		//Limpio la información recibida de mis satelites para un nuevo request
		satellitesMap.clearInfoData();
		
		return topSecret;
	}
	
	/**
	 * Obtengo de mis instancias de satelites la información guardada previamente
	 * y determino si tengo toda la información necesaria para devolver la posición del enemigo
	 */
	@Override
	public TopSecretResponse decodeAndLocalizeSplit() {
		TopSecretResponse topSecret = new TopSecretResponse();
		
		//Triangulo la ubicación de la nave imperial y obtengo el mensaje cifrado
		String message = getDecodeMessage();
		if(message == null) {
			return topSecret;
		}
		topSecret.setMessage(getDecodeMessage());
		
		Position position = getImperialShipLocation();
		topSecret.setPosition(position);
		
		return topSecret;
	}

	@Override
	public Satellite setDistanceAndMessage(SatelliteRequest satelliteRequest) {
		satellitesMap.completeDataForSatellite(satelliteRequest);
		return satellitesMap.getSatellites().get(satelliteRequest.getName());
	}
	
	public String getDecodeMessage() {
		//Obtengo los mensajes recibidos por los 3 satelites
		Object[] messagesObject = satellitesMap.getSatellites().values()
				.stream()
				.map(it -> it.getReceiptMessage())
				.toArray();
		
		String[][] messages = Arrays.copyOf(messagesObject, messagesObject.length, String[][].class);
		
		//Trato de descifrar el mensaje 
		return getMessage(messages);
	}
	
	public Position getImperialShipLocation() {
		/* Ya descifre el mensaje ahora quiero determinar la posición de la nave imperial
			por lo que obtengo las distancias de los satelites a la nave */
		double[] distances = satellitesMap.getSatellites().values()
				.stream()
				.mapToDouble(i -> i.getDistanceFromShip())
				.toArray();
		
		//Trato de triangular la posición
		return getLocation(distances);
	}

	/**
	 * Método que resuelve la posición de la nave imperial dadas 3 posiciones
	 * de satelites de los rebeldes. Se utiliza el concepto de trilateración para 
	 * determinar la posición de la nave (Implementando fn Nivel 1)
	 * @param distances
	 * @return posicion nave imperial
	 */
	public Position getLocation(double[] distances) {
		
		//Si no tengo todas las distancias hacia la nave no puede determinar su posición
		if(satellitesMap.getSatellites().values().stream().anyMatch(s -> s.getDistanceFromShip() == null)) {
			throw new NotEnoughInformationException("No hay suficiente información para triangular la ubicación "
					+ "y el mensaje enviado por la nave imperial");
		}
		
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
	 * Método que descifra el mensaje recibido en las distintas naves (Implementando fn Nivel 1)
	 * @param messages
	 * @return
	 */
	public String getMessage(String[] ... messages) {
		
		if(Arrays.stream(messages).anyMatch(Objects::isNull)) {
			throw new NotEnoughInformationException("No hay suficiente información para triangular la ubicación "
					+ "y el mensaje enviado por la nave imperial");
		}
		
		String[] decodeMessage = new String[messages[0].length];
		
		for(int j=0; j < messages[0].length; j++) {
			int i = 0;
			while(i < messages.length) {
				if(Strings.isNotBlank(messages[i][j])) {
					decodeMessage[j] = messages[i][j];
					break;
				}
				i++;
				if(i == messages.length) {
					return null;
				}
			}
		}
		
		return Strings.join(Arrays.asList(decodeMessage), ' ');
	}
	
}
