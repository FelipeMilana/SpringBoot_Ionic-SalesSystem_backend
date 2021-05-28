package com.projects.SalesSystem.entities.enums;

public enum Status {

	STOCK(1, "Estoque"),
	SOLD(2, "Vendido");
	
	private int code;
	private String description;
	
	private Status(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static Status toIntegerEnum(Integer code) {
		if(code == null) {
			return null;
		}
		
		for(Status status: Status.values()) {
			if(code.equals(status.getCode())) {
				return status;
			}
		}
		
		throw new IllegalArgumentException("Invalid id" + code);
	}
}
