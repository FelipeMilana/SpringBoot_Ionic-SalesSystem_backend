package com.projects.SalesSystem.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.projects.SalesSystem.entities.enums.Bank;

@Entity
@Table(name = "tb_consortium_payment")
public class ConsortiumPayment extends Payment{

	private static final long serialVersionUID = 1L;

	private String name;
	private String cnpj;
	private String telephone;
	private Long quota;
	
	@Column(name = "`GROUP`")
	private Long group;
	private Double inputValue;
	private Integer inputBank;
	private Double consortiumValue;
	private Integer consortiumBank;
	
	public ConsortiumPayment() {
	}
	
	public ConsortiumPayment(Long id, Sale sale, String name, String cnpj, String telephone, Long quota, Long group,
			Double inputvalue, Bank inputBank, Double consortiumValue, Bank consortiumBank) {
		super(id, sale);
		this.name = name;
		this.cnpj = cnpj;
		this.telephone = telephone;
		this.quota = quota;
		this.group = group;
		this.inputValue = inputvalue;
		this.inputBank = (inputBank==null) ? null : inputBank.getCode();
		this.consortiumValue = consortiumValue;
		this.consortiumBank = (consortiumBank==null) ? null : consortiumBank.getCode();
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
	
	public Bank getInputBank() {
		return Bank.toIntegerEnum(inputBank);
	}
	
	public void setInputBank(Bank inputBank) {
		this.inputBank = inputBank.getCode();
	}

	public Double getConsortiumValue() {
		return consortiumValue;
	}

	public void setConsortiumValue(Double consortiumValue) {
		this.consortiumValue = consortiumValue;
	}

	public Bank getConsortiumBank() {
		return Bank.toIntegerEnum(consortiumBank);
	}
	
	public void setConsortiumBank(Bank consortiumBank) {
		this.consortiumBank = consortiumBank.getCode();
	}
}