package com.mercadolibre.app.model;

import java.util.List;

public class TopSecretRequest {
	
	private List<SatelliteRequest> satellites;
	
	public TopSecretRequest() {
	}

	public List<SatelliteRequest> getSatellites() {
		return satellites;
	}

	public void setSatellites(List<SatelliteRequest> satellites) {
		this.satellites = satellites;
	}
	
}
