package com.mercadolibre.app.exceptions;

public class NotEnoughInformationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotEnoughInformationException(String message) {
		super(message);
	}

}
