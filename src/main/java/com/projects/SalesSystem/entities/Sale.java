package com.projects.SalesSystem.entities;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.projects.SalesSystem.entities.enums.Bank;

@Entity
@Table(name = "tb_sale")
public class Sale implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate date;
	private Double finalValue;
	private Integer bank;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private Vehicle vehicle;
	
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Person client;
	
	@ManyToOne
	@JoinColumn(name = "seller_id")
	private User seller;

	public Sale() {
	}
	
	public Sale(Long id, LocalDate date, Double finalValue, Bank bank, Vehicle vehicle, Person client, User seller) {
		this.id = id;
		this.date = date;
		this.finalValue = finalValue;
		this.bank = (bank==null) ? null : bank.getCode();
		this.vehicle = vehicle;
		this.client = client;
		this.seller = seller;
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
	
	public Bank getBank() {
		return Bank.toIntegerEnum(bank);
	}
	
	public void setBank(Bank bank) {
		this.bank = bank.getCode();
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Person getClient() {
		return client;
	}

	public void setClient(Person client) {
		this.client = client;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public Double getProfit() {
		return finalValue - vehicle.getPaidValue() - vehicle.getExpenses().stream().mapToDouble(x -> x.getValue()).sum();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sale other = (Sale) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}