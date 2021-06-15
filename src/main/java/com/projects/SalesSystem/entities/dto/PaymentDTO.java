package com.projects.SalesSystem.entities.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.projects.SalesSystem.entities.Payment;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@type")
public abstract class PaymentDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Integer paymentType;
	
	public PaymentDTO() {
	}
	
	public PaymentDTO(Payment obj) {
		id = obj.getId();
		paymentType = (obj.getPaymentType()==null) ? null : obj.getPaymentType().getCode();
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getPaymentType() {
		return paymentType;
	}
	
	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}
}
