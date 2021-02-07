package com.mercadolibre.api.model;

public class TopSecretResponse {

	private Position position;
	private String message;

	public TopSecretResponse() {
	}

	public TopSecretResponse(Position position, String message) {
		this.position = position;
		this.message = message;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.position.equals(((TopSecretResponse)obj).getPosition()) &&
				this.message.equals(((TopSecretResponse)obj).getMessage());
	}

}
