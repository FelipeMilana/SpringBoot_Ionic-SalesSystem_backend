package com.projects.SalesSystem.entities.dto;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.projects.SalesSystem.entities.CashWithExchangePayment;

@JsonTypeName("pagamentoÀVistaComTroca")
public class CashWithExchangePaymentDTO extends PaymentDTO {

	private static final long serialVersionUID = 1L;

	private Integer bank;
	
	@PositiveOrZero(message = "O valor não pode ser negativo")
	private Double cashValue;
	
	@Valid
	private ExchangeVehicleDTO exchangeVehicle;
	
	public CashWithExchangePaymentDTO() {
	}
	
	public CashWithExchangePaymentDTO(CashWithExchangePayment obj) {
		super.setId(obj.getId());
		super.setPaymentType(obj.getPaymentType().getCode());
		bank = (obj.getBank()==null) ? null : obj.getBank().getCode();
		cashValue = obj.getCashValue();
		exchangeVehicle = new ExchangeVehicleDTO(obj.getVehicle());
	}
	
	public Integer getBank() {
		return bank;
	}
	
	public void setBank(Integer bank) {
		this.bank = bank;
	}
	
	public Double getCashValue() {
		return cashValue;
	}
	
	public void setCashValue(Double cashValue) {
		this.cashValue = cashValue;
	}
	
	public ExchangeVehicleDTO getExchangeVehicle() {
		return exchangeVehicle;
	}
	
	public void setExchangeVehicle(ExchangeVehicleDTO exchangevehicle) {
		this.exchangeVehicle = exchangevehicle;
	}
}
