package com.mercadolibre.app.model;

public class SatelliteRequest {

	private String name;
	private double distance;
	private String[] message;

	public SatelliteRequest() {
	}

	public SatelliteRequest(String name, double distance, String[] message) {
		this.name = name;
		this.distance = distance;
		this.message = message;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String[] getMessage() {
		return message;
	}

	public void setMessage(String[] message) {
		this.message = message;
	}

}
