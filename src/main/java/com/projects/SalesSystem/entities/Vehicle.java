package com.projects.SalesSystem.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.projects.SalesSystem.entities.enums.Bank;
import com.projects.SalesSystem.entities.enums.Status;
import com.projects.SalesSystem.entities.enums.VehicleType;

@Entity
@Table(name = "tb_vehicle")
public class Vehicle  implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Integer type;
	private String brand;
	private String model;
	private String version;
	private String fabYear;
	private String modYear;
	private String color;
	private String motor;
	private LocalDate date;
	private String licensePlate;
	private String chassi;
	private String renavam;
	private String description;
	private Double paidValue;
	private Integer bank;
	private Double possibleSellValue;
	private Integer status;
	
	//associations
	@OneToMany(mappedBy = "vehicle")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Expense> expenses = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "buyer_id")
	private User buyer;
	
	@ManyToOne
	@JoinColumn(name = "seller_id")
	private Person seller;
	
	public Vehicle() {
	}

	public Vehicle(Long id, VehicleType type, String brand, String model, String version, String fabYear, String modYear, String color,
			String motor, LocalDate date, String licensePlate, String chassi, String renavam, String description, Double paidValue, 
			Bank bank, Double possibleSellValue, Status status, User buyer, Person seller) {
		this.id = id;
		this.type = (type==null) ? null : type.getCode();
		this.brand = brand;
		this.model = model;
		this.version = version;
		this.fabYear = fabYear;
		this.modYear = modYear;
		this.color = color;
		this.motor = motor;
		this.date = date;
		this.licensePlate = licensePlate;
		this.chassi = chassi;
		this.renavam = renavam;
		this.description = description;
		this.paidValue = paidValue;
		this.bank = (bank==null) ? null : bank.getCode();
		this.possibleSellValue = possibleSellValue;
		this.status = (status==null) ? null : status.getCode();
		this.buyer = buyer;
		this.seller = seller;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public VehicleType getType() {
		return VehicleType.toIntegerEnum(type);
	}

	public void setType(VehicleType type) {
		this.type = type.getCode();
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
		this.version = version;
	}
	
	public String getFabYear() {
		return fabYear;
	}
	
	public void setFabYear(String fabYear) {
		this.fabYear = fabYear;
	}
	
	public String getModYear() {
		return modYear;
	}
	
	public void setModYear(String modYear) {
		this.modYear = modYear;
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
	}
	
	public String getMotor() {
		return motor;
	}
	
	public void setMotor(String motor) {
		this.motor = motor;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getChassi() {
		return chassi;
	}
	
	public void setChassi(String chassi) {
		this.chassi = chassi;
	}
	
	public String getRenavam() {
		return renavam;
	}
	
	public void setRenavam(String renavam) {
		this.renavam = renavam;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getPaidValue() {
		return paidValue;
	}

	public void setPaidValue(Double paidValue) {
		this.paidValue = paidValue;
	}

	public Bank getBank() {
		return Bank.toIntegerEnum(bank);
	}

	public void setBank(Bank bank) {
		this.bank = bank.getCode();
	}

	public Double getPossibleSellValue() {
		return possibleSellValue;
	}

	public void setPossibleSellValue(Double possibleSellValue) {
		this.possibleSellValue = possibleSellValue;
	}

	public Status getStatus() {
		return Status.toIntegerEnum(status);
	}

	public void setStatus(Status status) {
		this.status = status.getCode();
	}
	
	public List<Expense> getExpenses() {
		return expenses;
	}

	public User getBuyer() {
		return buyer;
	}

	public void setBuyer(User buyer) {
		this.buyer = buyer;
	}

	public Person getSeller() {
		return seller;
	}

	public void setSeller(Person seller) {
		this.seller = seller;
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
		Vehicle other = (Vehicle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}