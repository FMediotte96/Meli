package com.mercadolibre.app.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.util.Strings;
import org.apache.tomcat.util.buf.StringUtils;
import org.springframework.stereotype.Service;

import com.mercadolibre.app.exceptions.SatelliteDoesNotExistsException;
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
	public TopSecretResponse decodeAndLocalize(List<SatelliteRequest> satellitesRequest) throws SatelliteDoesNotExistsException {
		
		TopSecretResponse topSecret = new TopSecretResponse();
		//Guardo la información recibida en mis satelites rebeldes
		for(SatelliteRequest s : satellitesRequest) {
			satellitesMap.completeDataForSatellite(s);
		}
		
		//Triangulo la ubicación de la nave imperial y obtengo el mensaje cifrado
		topSecret = getLocationAndMessage();
		
		//Limpio la información recibida de mis satelites para un nuevo request
		satellitesMap.clearInfoData();
		
		return topSecret;
	}
	
	public TopSecretResponse getLocationAndMessage() {
		TopSecretResponse topSecret = new TopSecretResponse();
		
		//Primero calculo el mensaje cifrado
		Object[] messagesObject = satellitesMap.getSatellites().values()
				.stream()
				.map(it -> it.getReceiptMessage())
				.toArray();
		
		String[][] messages = Arrays.copyOf(messagesObject, messagesObject.length, String[][].class);
		
		String message = getMessage(messages);
		
		//Si no pude descifrar el mensaje no calculo la posición
		if(message == null){
			return topSecret;
		}
		topSecret.setMessage(message);
		
		//Ya descifre el mensaje ahora quiero determinar la posición de la nave imperial
		double[] distances = satellitesMap.getSatellites().values()
				.stream()
				.mapToDouble(i -> i.getDistanceFromShip())
				.toArray();
		Position pos = getLocation(distances);
		
		//Si no me fue posible determinar la posición retorno el topSecret solo con el msj
		if(pos == null) {
			return topSecret;
		}
		
		topSecret.setPosition(pos);
		return topSecret;
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
		
		return StringUtils.join(Arrays.asList(decodeMessage), ' ');
	}


	/**
	 * Obtengo de mis instancias de satelites la información guardada previamente
	 * y determino si tengo toda la información necesaria para devolver la posición del enemigo
	 */
	@Override
	public TopSecretResponse decodeAndLocalizeSplit() {
		TopSecretResponse topSecret = new TopSecretResponse();
		
		//Obtengo los mensajes vacios de cada nave
		List<String[]> messages = satellitesMap.getSatellites().values()
											.stream()
											.map(it -> it.getReceiptMessage())
											.filter(it -> it == null)
											.collect(Collectors.toList());
		
		//Si no tengo todas las parte del mensaje no puedo decodificar el mismo
		if(!messages.isEmpty()) {
			return topSecret;
		}
		
		//Obtengo las distancias que no estan seteadas aún en mi singleton
		double[] distances = satellitesMap.getSatellites().values()
											.stream()
											.mapToDouble(s -> s.getDistanceFromShip())
											.filter(distance -> distance == 0.0)
											.toArray();
		
		//Si no tengo todas las distancias hacia la nave no puede determinar su posición
		if(distances.length != 0) {
			return topSecret;
		}
		
		//Si llegue hasta acá es porque tengo la información suficiente, 
		//sin embargo puede pasar de que no se pueda triangular la posicion
		topSecret = getLocationAndMessage();
		
		return topSecret;
	}

	@Override
	public Satellite setDistanceAndMessage(SatelliteRequest satelliteRequest) throws SatelliteDoesNotExistsException {
		satellitesMap.completeDataForSatellite(satelliteRequest);
		return satellitesMap.getSatellites().get(satelliteRequest.getName());
	}
	
}
