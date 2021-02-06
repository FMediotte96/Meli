package com.mercadolibre.api.model;

public class TopSecretRequest {

	private SatelliteRequest[] satellites;

	public TopSecretRequest() {
	}

	public SatelliteRequest[] getSatellites() {
		return satellites;
	}

	public void setSatellites(SatelliteRequest[] satellites) {
		this.satellites = satellites;
	}

}
