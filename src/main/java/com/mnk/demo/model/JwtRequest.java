package com.mnk.demo.model;


public class JwtRequest {
	
	private static String username;
	 private static String password;
	 
	public JwtRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public static String getUsername() {
		return username;
	}
	public static String getPassword() {
		return password;
	}
	 

}
