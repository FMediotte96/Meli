package com.mercadolibre.app.exceptions;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mercadolibre.app.model.ErrorMessage;

@ControllerAdvice
public class AppExceptionsHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
		String errorMessageDescription = ex.getLocalizedMessage();

		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}

		return new ResponseEntity<>(errorMessageDescription, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { NotEnoughInformationException.class })
	public ResponseEntity<Object> handleNotEnoughInformationException(Exception ex, WebRequest request) {
		String errorMessageDescription = ex.getLocalizedMessage();

		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}
		
		ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);

		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { SatelliteDoesNotExistsException.class })
	public ResponseEntity<Object> handleSatelliteDoesNotExistsException(Exception ex, WebRequest request) {
		String errorMessageDescription = ex.getLocalizedMessage();

		if (errorMessageDescription == null) {
			errorMessageDescription = ex.toString();
		}
		
		ErrorMessage errorMessage = new ErrorMessage(new Date(), errorMessageDescription);

		return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

}
