package com.api.blog.helper;


public class JwtRequest{
	

	private String username;
	private String password;
	
	//need default constructor for JSON Parsing
	public JwtRequest()
	{
		
	}
	
	public JwtRequest(String username, String password) {
		this.setUsername(username);
		this.setPassword(password);
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
		return "JwtRequest [username=" + username + ", password=" + password + "]";
	}

}
