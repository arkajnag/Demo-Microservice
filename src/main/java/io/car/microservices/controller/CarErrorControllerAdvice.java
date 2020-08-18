package io.car.microservices.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import io.car.microservices.exceptionhandler.DataNotAllowedException;
import io.car.microservices.exceptionhandler.DataNotFoundException;
import io.car.microservices.model.ErrorModel;

@ControllerAdvice
public class CarErrorControllerAdvice extends ResponseEntityExceptionHandler{
	
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
			HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorModel errorObj=new ErrorModel(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).contentType(MediaType.APPLICATION_JSON).body(errorObj);
	}
	
	@ExceptionHandler(DataNotAllowedException.class)
	public ResponseEntity<ErrorModel> mappingException(DataNotAllowedException ex) {
		ErrorModel errorObj=new ErrorModel(HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).contentType(MediaType.APPLICATION_JSON).body(errorObj);
	}
	
	@ExceptionHandler(DataNotFoundException.class)
	public ResponseEntity<ErrorModel> mappingException(DataNotFoundException ex) {
		ErrorModel errorObj=new ErrorModel(HttpStatus.NOT_FOUND.value(), ex.getMessage(), LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).contentType(MediaType.APPLICATION_JSON).body(errorObj);
	}

}
