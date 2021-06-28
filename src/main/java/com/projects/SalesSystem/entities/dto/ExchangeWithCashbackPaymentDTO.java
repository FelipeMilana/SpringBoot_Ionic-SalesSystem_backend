package com.projects.SalesSystem.entities.dto;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.projects.SalesSystem.entities.ExchangeWithCashbackPayment;

@JsonTypeName("pagamentoTrocaComTroco")
public class ExchangeWithCashbackPaymentDTO extends PaymentDTO {

	private static final long serialVersionUID = 1L;
	
	@PositiveOrZero(message = "O valor não pode ser negativo")
	private Double cashback;
	
	private Integer bank;
	
	@Valid
	private ExchangeVehicleDTO exchangeVehicle;
	
	public ExchangeWithCashbackPaymentDTO() {
	}
	
	public ExchangeWithCashbackPaymentDTO(ExchangeWithCashbackPayment obj) {
		super.setId(obj.getId());
		super.setPaymentType(obj.getPaymentType().getCode());
		cashback = obj.getCashback();
		bank = (obj.getBank()==null) ? null : obj.getBank().getCode();
		exchangeVehicle = new ExchangeVehicleDTO(obj.getVehicle());
	}
	
	public Double getCashback() {
		return cashback;
	}
	
	public void setCashback(Double cashback) {
		this.cashback = cashback;
	}
	
	public Integer getBank() {
		return bank;
	}
	
	public void setBank(Integer bank) {
		this.bank = bank;
	}
	
	public ExchangeVehicleDTO getExchangeVehicle() {
		return exchangeVehicle;
	}
	
	public void setExchangeVehicle(ExchangeVehicleDTO exchangevehicle) {
		this.exchangeVehicle = exchangevehicle;
	}
}
