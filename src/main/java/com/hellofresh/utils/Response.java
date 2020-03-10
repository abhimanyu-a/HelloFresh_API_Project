package com.hellofresh.utils;

public enum Response {

	OK(200, "OK"),
	CREATED(201, "Created"),
	BAD_REQUEST(400, "Bad Request"),
	UNAUTHORIZED(401, "Unauthorized"), 
	FORBIDDEN(403, "Forbidden"), 
	NOT_FOUND(404, "Not Found"), 
	Internal_Server_Error(500,"Internal Server Error"),
	ERROR(409, "Error"),;

	private final int code;
	private final String description;

	private Response(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public int getCode() {
		return code;
	}

	@Override
	public String toString() {
		return code + ": " + description;
	}
}