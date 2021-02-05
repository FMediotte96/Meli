package com.mercadolibre.api.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mercadolibre.api.model.ErrorMessage;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorMessage handleAnyException(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				LocalDateTime.now(), 
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getMessage(),
				request.getDescription(false));
		
		return message;
	}
	

	@ExceptionHandler(value = { EntityNotFoundException.class, NotEnoughInformationException.class })
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ErrorMessage handleNotFoundException(Exception ex, WebRequest request) {
		ErrorMessage message = new ErrorMessage(
				LocalDateTime.now(), 
				HttpStatus.NOT_FOUND.value(),
				ex.getMessage(),
				request.getDescription(false));
		
		return message;
	}

}
