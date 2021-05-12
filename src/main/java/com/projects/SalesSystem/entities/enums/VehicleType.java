package com.projects.SalesSystem.entities.enums;

public enum VehicleType {

	CAR(1, "Carro"),
	MOTORCYCLE(2, "Motocicleta");
	
	private int code;
	private String description;
	
	private VehicleType(int code, String description) {
		this.code = code;
		this.description = description;
	}

	public int getCode() {
		return code;
	}

	public String getDescription() {
		return description;
	}
	
	public static VehicleType toIntegerEnum(Integer code) {
		if(code == null) {
			return null;
		}
		
		for(VehicleType vt: VehicleType.values()) {
			if(code.equals(vt.getCode())) {
				return vt;
			}
		}
		
		throw new IllegalArgumentException("Invalid id" + code);
	}
}
