package com.mercadolibre.app.service;

import java.util.List;

import com.mercadolibre.app.exceptions.SatelliteDoesNotExistsException;
import com.mercadolibre.app.model.Satellite;
import com.mercadolibre.app.model.SatelliteRequest;
import com.mercadolibre.app.model.TopSecretResponse;

public interface ITopSecretService {

	TopSecretResponse decodeAndLocalize(List<SatelliteRequest> satellitesRequest) throws SatelliteDoesNotExistsException;
	TopSecretResponse decodeAndLocalizeSplit();
	Satellite setDistanceAndMessage(SatelliteRequest satelliteRequest) throws SatelliteDoesNotExistsException;
}
