package com.projects.SalesSystem.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.projects.SalesSystem.entities.enums.PaymentType;

@Entity
@Table(name = "tb_payment")
@Inheritance(strategy=InheritanceType.JOINED)

public abstract class Payment implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private Integer paymentType;
	
	@OneToOne
	@JoinColumn(name = "sale_id")
	@MapsId
	private Sale sale;
	
	public Payment() {
	}
	
	public Payment(Long id, Sale sale) {
		this.id = id;
		this.sale = sale;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Sale getSale() {
		return sale;
	}
	
	public void setSale(Sale sale) {
		this.sale = sale;
	}

	public PaymentType getPaymentType() {
		return PaymentType.toIntegerEnum(paymentType);
	}
	
	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType.getCode();
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
		Payment other = (Payment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}