package com.projects.SalesSystem.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.projects.SalesSystem.entities.enums.Bank;

@Entity
@Table(name = "tb_cash_payment")
public class CashPayment extends Payment{

	private static final long serialVersionUID = 1L;

	private Integer bank;
	
	public CashPayment() {
	}
	
	public CashPayment(Long id, Sale sale, Bank bank) {
		super(id, sale);
		this.bank = (bank==null) ? null : bank.getCode();
	}
	
	public Bank getBank() {
		return Bank.toIntegerEnum(bank);
	}
	
	public void setBank(Bank bank) {
		this.bank = bank.getCode();
	}
}
