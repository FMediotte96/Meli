package com.mercadolibre.api.exceptions;

public class NotEnoughInformationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotEnoughInformationException(String message) {
		super(message);
	}

}
