package com.projects.SalesSystem.services.exceptions;

public class DataIntegrity extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public DataIntegrity(String msg) {
		super(msg);
	}
}