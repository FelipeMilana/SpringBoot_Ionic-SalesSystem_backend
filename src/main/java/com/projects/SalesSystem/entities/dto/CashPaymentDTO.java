package com.projects.SalesSystem.entities.dto;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.projects.SalesSystem.entities.CashPayment;

@JsonTypeName("pagamentoÀVista")
public class CashPaymentDTO extends PaymentDTO {

	private static final long serialVersionUID = 1L;

	private Integer bank;
	
	public CashPaymentDTO() {
	}
	
	public CashPaymentDTO(CashPayment obj) {
		super.setId(obj.getId());
		super.setPaymentType(obj.getPaymentType().getCode());
		bank = (obj.getBank()==null) ? null : obj.getBank().getCode();
	}
	
	public Integer getBank() {
		return bank;
	}
	
	public void setBank(Integer bank) {
		this.bank = bank;
	}
}
