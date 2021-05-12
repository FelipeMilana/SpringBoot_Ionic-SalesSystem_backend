package com.projects.SalesSystem.entities.enums;

public enum Bank {

	SANTANDER(1, "Santander"),
	NUBANK(2, "Nubank");
	
	private int code;
	private String description;
	
	private Bank(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static Bank toIntegerEnum(Integer code) {
		if(code == null) {
			return null;
		}
		
		for(Bank bank: Bank.values()) {
			if(code.equals(bank.getCode())) {
				return bank;
			}
		}
		
		throw new IllegalArgumentException("Invalid id" + code);
	}
}
