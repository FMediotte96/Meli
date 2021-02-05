package com.mercadolibre.api.service;

import com.mercadolibre.api.model.Satellite;
import com.mercadolibre.api.model.SatelliteRequest;
import com.mercadolibre.api.model.TopSecretResponse;

public interface ITopSecretService {

	TopSecretResponse decodeAndLocalize(SatelliteRequest[] satellitesRequest);
	TopSecretResponse decodeAndLocalizeSplit();
	Satellite setDistanceAndMessage(SatelliteRequest satelliteRequest);
}
