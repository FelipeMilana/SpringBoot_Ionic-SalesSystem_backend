package com.projects.SalesSystem.entities.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.projects.SalesSystem.entities.CashPayment;
import com.projects.SalesSystem.entities.CashWithExchangePayment;
import com.projects.SalesSystem.entities.ConsortiumPayment;
import com.projects.SalesSystem.entities.ConsortiumWithExchangePayment;
import com.projects.SalesSystem.entities.ExchangeWithCashbackPayment;
import com.projects.SalesSystem.entities.FundedPayment;
import com.projects.SalesSystem.entities.FundedWithExchangePayment;
import com.projects.SalesSystem.entities.Sale;

public class SaleDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	@JsonFormat(pattern = "dd/MM/yyyy")
	private LocalDate date;
	
	@NotNull(message = "Preenchimento Obrigatório")
	@Positive(message  = "Valor precisa ser positivo")
	private Double finalValue;
	
	private Double profit;
	private VehicleDTO vehicle;
	
	@NotNull(message = "Preenchimento Obrigatório")
	@Valid
	private PaymentDTO payment;
	
	@Valid
	private PersonDTO client;
	
	public SaleDTO() {
	}
	
	public SaleDTO(Sale obj) {
		id = obj.getId();
		date = obj.getDate();
		finalValue = obj.getFinalValue();
		profit = obj.getProfit();
		vehicle = new VehicleDTO(obj.getVehicle());
		client = new PersonDTO(obj.getClient());
		
		if(obj.getPayment() instanceof CashPayment) {
			CashPayment pay = (CashPayment) obj.getPayment();
			payment = new CashPaymentDTO(pay);
		}
		
		else if(obj.getPayment() instanceof CashWithExchangePayment) {
			CashWithExchangePayment pay = (CashWithExchangePayment) obj.getPayment();
			payment = new CashWithExchangePaymentDTO(pay);
		}
		
		else if(obj.getPayment() instanceof FundedPayment) {
			FundedPayment pay = (FundedPayment) obj.getPayment();
			payment = new FundedPaymentDTO(pay);
		}
		else if(obj.getPayment() instanceof FundedWithExchangePayment) {
			FundedWithExchangePayment pay = (FundedWithExchangePayment) obj.getPayment();
			payment = new FundedWithExchangePaymentDTO(pay);
		}
		
		else if(obj.getPayment() instanceof ConsortiumPayment) {
			ConsortiumPayment pay = (ConsortiumPayment) obj.getPayment();
			payment = new ConsortiumPaymentDTO(pay);
		}
		
		else if(obj.getPayment() instanceof ConsortiumWithExchangePayment){
			ConsortiumWithExchangePayment pay = (ConsortiumWithExchangePayment) obj.getPayment();
			payment = new ConsortiumWithExchangePaymentDTO(pay);
		}
		
		else {
			ExchangeWithCashbackPayment pay = (ExchangeWithCashbackPayment) obj.getPayment();
			payment = new ExchangeWithCashbackPaymentDTO(pay);
		}
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public Double getFinalValue() {
		return finalValue;
	}
	
	public void setFinalValue(Double finalValue) {
		this.finalValue = finalValue;
	}
	
	public Double getProfit() {
		return profit;
	}

	public VehicleDTO getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleDTO vehicle) {
		this.vehicle = vehicle;
	}

	public PersonDTO getClient() {
		return client;
	}

	public void setClient(PersonDTO client) {
		this.client = client;
	}
	
	public PaymentDTO getPayment() {
		return payment;
	}
	
	public void setPayment(PaymentDTO payment) {
		this.payment = payment;
	}
}