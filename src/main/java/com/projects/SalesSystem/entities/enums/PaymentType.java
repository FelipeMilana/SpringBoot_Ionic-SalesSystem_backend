package com.projects.SalesSystem.entities.enums;

public enum PaymentType {

	CASH(1, "À Vista"),
	FUNDED(2, "Financiamento"),
	CONSORTIUM(3, "Consórcio"),
	CASHWITHEXCHANGE(4, "À Vista + Troca"),
	FUNDEDWITHEXCHANGE(5, "Financiamento + Troca"),
	CONSORTIUMWITHEXCHANGE(6, "Consórcio + Troca");
	
	private int code;
	private String description;
	
	private PaymentType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static PaymentType toIntegerEnum(Integer code) {
		if(code == null) {
			return null;
		}
		
		for(PaymentType pay: PaymentType.values()) {
			if(code.equals(pay.getCode())) {
				return pay;
			}
		}
		
		throw new IllegalArgumentException("Invalid id" + code);
	}
}