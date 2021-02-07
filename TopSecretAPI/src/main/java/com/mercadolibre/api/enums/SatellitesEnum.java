package com.mercadolibre.api.enums;

public enum SatellitesEnum {
	KENOBI("kenobi"),
	SKYWALKER("skywalker"),
	SATO("sato");
	
	private String name;

	SatellitesEnum(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
}
