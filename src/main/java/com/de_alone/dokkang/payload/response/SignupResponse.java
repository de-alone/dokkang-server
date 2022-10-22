package com.de_alone.dokkang.payload.response;

public class SignupResponse {
	private String status;

	public SignupResponse(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
