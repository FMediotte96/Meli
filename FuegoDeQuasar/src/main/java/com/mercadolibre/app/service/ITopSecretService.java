package com.mercadolibre.app.service;

import com.mercadolibre.app.model.Satellite;
import com.mercadolibre.app.model.SatelliteRequest;
import com.mercadolibre.app.model.TopSecretResponse;

public interface ITopSecretService {

	TopSecretResponse decodeAndLocalize(SatelliteRequest[] satellitesRequest);
	TopSecretResponse decodeAndLocalizeSplit();
	Satellite setDistanceAndMessage(SatelliteRequest satelliteRequest);
}
