package io.car.microservices.model;

import java.time.LocalDateTime;

/**
 * Error Handling Model Class to set the Customized Error Message while handling Error/Exception.
 */
public class ErrorModel {
	
	private Integer errorCode;
	private String errorMessage;
	private LocalDateTime timestamp;
	public Integer getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public ErrorModel(Integer errorCode, String errorMessage, LocalDateTime timestamp) {
		this.errorCode = errorCode;
		this.errorMessage = errorMessage;
		this.timestamp = timestamp;
	}
	public ErrorModel() {
		
	}
	@Override
	public String toString() {
		return "ErrorModel [errorCode=" + errorCode + ", errorMessage=" + errorMessage + ", timestamp=" + timestamp
				+ "]";
	}
}
