package com.mercadolibre.api.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ErrorMessage {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private int statusCode;
	private String message;
	private String description;

	public ErrorMessage(LocalDateTime timestamp, int statusCode, String message, String description) {
		this.timestamp = timestamp;
		this.statusCode = statusCode;
		this.message = message;
		this.description = description;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public String getMessage() {
		return message;
	}

	public String getDescription() {
		return description;
	}

}