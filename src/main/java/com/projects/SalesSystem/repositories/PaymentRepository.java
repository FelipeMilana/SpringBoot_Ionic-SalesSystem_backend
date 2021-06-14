package com.projects.SalesSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.SalesSystem.entities.Payment;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
