package com.mercadolibre.app.exceptions;

public class SatelliteDoesNotExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public SatelliteDoesNotExistsException(String message) {
		super(message);
	}

}
