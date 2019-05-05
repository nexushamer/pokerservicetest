package com.logmein.pokergame.service.pokergame.exceptions.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.logmein.pokergame.service.pokergame.exceptions.RegisterAlreadyExistsInsideTheGameException;
import com.logmein.pokergame.service.pokergame.exceptions.RegisterDoesNotExistsException;
import com.logmein.pokergame.service.pokergame.exceptions.RequestDataInvalidException;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {
	private static final String REGISTER_DOES_NOT_EXISTS = "The register does not exist in the DB";
	private static final String DECK_ALREADY_EXISTS = "The register already exists inside the game";
	private static final String INVALID_REQUEST_EXISTS = "The requist received is invalid, please check the data";
	
	@ExceptionHandler({ RegisterDoesNotExistsException.class })
    public ResponseEntity<Object> handleRegisterDoesNotExistsException(
      Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
        		REGISTER_DOES_NOT_EXISTS, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler({ RegisterAlreadyExistsInsideTheGameException.class })
    public ResponseEntity<Object> handleDeckAlreadyExistsInsideTheGameException(
      Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
        		DECK_ALREADY_EXISTS, new HttpHeaders(), HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler({ RequestDataInvalidException.class })
    public ResponseEntity<Object> handleRequestDataInvalidException(
      Exception ex, WebRequest request) {
        return new ResponseEntity<Object>(
        		INVALID_REQUEST_EXISTS, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }
	
}
