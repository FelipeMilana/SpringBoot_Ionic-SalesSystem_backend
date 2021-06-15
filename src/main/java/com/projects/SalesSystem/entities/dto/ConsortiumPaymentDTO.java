package com.projects.SalesSystem.entities.dto;

import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.br.CNPJ;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.projects.SalesSystem.entities.ConsortiumPayment;

@JsonTypeName("pagamentoPorConsorcio")
public class ConsortiumPaymentDTO extends PaymentDTO {

	private static final long serialVersionUID = 1L;

	private String name;
	
	@CNPJ
	private String cnpj;
	private String telephone;
	private Long quota;
	private Long group;
	
	@PositiveOrZero(message = "O valor não pode ser negativo")
	private Double inputValue;
	private Integer inputBank;
	
	@PositiveOrZero(message = "O valor não pode ser negativo")
	private Double consortiumValue;
	private Integer consortiumBank;
	
	public ConsortiumPaymentDTO() {
	}
	
	public ConsortiumPaymentDTO(ConsortiumPayment obj) {
		super.setId(obj.getId());
		super.setPaymentType(obj.getPaymentType().getCode());
		name = obj.getName();
		cnpj = obj.getCnpj();
		telephone = obj.getTelephone();
		quota = obj.getQuota();
		group = obj.getGroup();
		inputValue = obj.getInputValue();
		inputBank = (obj.getInputBank()==null) ? null : obj.getInputBank().getCode();
		consortiumValue = obj.getConsortiumValue();
		consortiumBank = (obj.getConsortiumBank()==null) ? null : obj.getConsortiumBank().getCode();
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

	public Long getQuota() {
		return quota;
	}
	
	public void setQuota(Long quota) {
		this.quota = quota;
	}
	
	public Long getGroup() {
		return group;
	}
	
	public void setGroup(Long group) {
		this.group = group;
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

	public Double getConsortiumValue() {
		return consortiumValue;
	}

	public void setConsortiumValue(Double consortiumValue) {
		this.consortiumValue = consortiumValue;
	}

	public Integer getConsortiumBank() {
		return consortiumBank;
	}
	
	public void setConsortiumBank(Integer consortiumBank) {
		this.consortiumBank = consortiumBank;
	}
}
