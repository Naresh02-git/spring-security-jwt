package com.mnk.demo.model;

public class JwtResponse {

	private String jwt;
	

	public JwtResponse(String jwtToken) {
		
		this.jwt = jwt;
	}


	public String getJwt() {
		return jwt;
	}
	
}
