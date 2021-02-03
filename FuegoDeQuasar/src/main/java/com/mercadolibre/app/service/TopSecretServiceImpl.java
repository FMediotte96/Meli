package com.mercadolibre.app.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.mercadolibre.app.model.Position;
import com.mercadolibre.app.model.Satellite;
import com.mercadolibre.app.model.SatelliteRequest;
import com.mercadolibre.app.model.TopSecretResponse;
import com.mercadolibre.app.util.CircleUtils;

@Service
public class TopSecretServiceImpl implements ITopSecretService {
	
	//Singleton
	private static final Satellite KENOBI = new Satellite("Kenobi", new Position(0,0));
	private static final Satellite SKYWALKER = new Satellite("Skywalker", new Position(10,10));
	private static final Satellite SATO = new Satellite("Sato", new Position(0,20));
	
	@Override
	public TopSecretResponse decodeAndLocalize(List<SatelliteRequest> satellitesRequest) {
		TopSecretResponse topSecret = new TopSecretResponse();
		double[] distances = getDistances(satellitesRequest);
		List<String[]> messages = satellitesRequest.stream().map(it -> it.getMessage()).collect(Collectors.toList());
		topSecret.setPosition(getLocation(distances));
		topSecret.setMessage(getMessage(messages));
		return topSecret;
	}

	private double[] getDistances(List<SatelliteRequest> satellitesRequest) {
		double[] distances = new double[satellitesRequest.size()];
		for(int i = 0; i < satellitesRequest.size(); i++) {
			distances[i] = satellitesRequest.get(i).getDistance();
		}
		return distances;
	}

	public Position getLocation(double[] distances) {
		Position result = CircleUtils.trilateracion(KENOBI, SKYWALKER, SATO, 
				distances[0], distances[1], distances[2]);
		return result;
	}
		
	
	public String getMessage(List<String[]> messages) {
		
		String[] decodeMessage = new String[messages.get(0).length];
		
		for(int i = 0; i< messages.get(0).length; i++) {
			decodeMessage[i] = "";
			for(int j = 0; j < messages.size(); j++) {
				if(!messages.get(j)[i].isEmpty()) {
					decodeMessage[i] = messages.get(j)[i];
					break;
				}
			}
			if(decodeMessage[i].isEmpty()) {
				return null;
			}
		}
		
		return Arrays.asList(decodeMessage).stream().collect(Collectors.joining(" "));
	}

	@Override
	public TopSecretResponse decodeAndLocalizeSplit(SatelliteRequest satelliteRequest) {
		// TODO Implements
		return null;
	}
	
}
