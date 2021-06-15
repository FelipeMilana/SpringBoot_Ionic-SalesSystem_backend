package com.projects.SalesSystem.entities.dto;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.projects.SalesSystem.entities.FundedWithExchangePayment;

@JsonTypeName("pagamentoFinanciadoComTroca")
public class FundedWithExchangePaymentDTO extends PaymentDTO {

	private static final long serialVersionUID = 1L;

	private String name;
	
	@CNPJ
	private String cnpj;
	private String telephone;
	
	@PositiveOrZero(message = "O valor não pode ser negativo")
	private Double inputValue;
	private Integer inputBank;
	
	@PositiveOrZero(message = "O valor não pode ser negativo")
	private Double fundedValue;
	private Integer fundedBank;
	
	@Valid
	private ExchangeVehicleDTO exchangeVehicle;
	
	public FundedWithExchangePaymentDTO() {
	}
	
	public FundedWithExchangePaymentDTO(FundedWithExchangePayment obj) {
		super.setId(obj.getId());
		super.setPaymentType(obj.getPaymentType().getCode());
		name = obj.getName();
		cnpj = obj.getCnpj();
		telephone = obj.getTelephone();
		inputValue = obj.getInputValue();
		inputBank = (obj.getInputBank()==null) ? null : obj.getInputBank().getCode();
		fundedValue = obj.getFundedValue();
		fundedBank = (obj.getFundedBank()==null) ? null : obj.getFundedBank().getCode();
		exchangeVehicle = new ExchangeVehicleDTO(obj.getVehicle());
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Double getInputValue() {
		return inputValue;
	}

	public void setInputValue(Double inputValue) {
		this.inputValue = inputValue;
	}

	public Integer getInputBank() {
		return inputBank;
	}
	
	public void setInputBank(Integer inputBank) {
		this.inputBank = inputBank;
	}
	
	public Double getFundedValue() {
		return fundedValue;
	}

	public void setFundedValue(Double fundedValue) {
		this.fundedValue = fundedValue;
	}
	
	public Integer getFundedBank() {
		return fundedBank;
	}
	
	public void setFundedBank(Integer fundedBank) {
		this.fundedBank = fundedBank;
	}
	
	public ExchangeVehicleDTO getExchangeVehicle() {
		return exchangeVehicle;
	}
	
	public void setExchangeVehicle(ExchangeVehicleDTO exchangeVehicle) {
		this.exchangeVehicle = exchangeVehicle;
	}
}
