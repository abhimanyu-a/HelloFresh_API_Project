package com.hellofresh.modals;

import java.util.List;

public class ErrorModal
{
	private String timestamp;

	private int status;

	private String error;

	private List<String> errors;

	private String message;

	private String path;

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getTimestamp() {
		return this.timestamp;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return this.status;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getError() {
		return this.error;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public List<String> getErrors() {
		return this.errors;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getPath() {
		return this.path;
	}
}
