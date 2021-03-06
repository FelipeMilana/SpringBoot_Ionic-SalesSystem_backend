package com.projects.SalesSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.SalesSystem.entities.Expense;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long>{

}
