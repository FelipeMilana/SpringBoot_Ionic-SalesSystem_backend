package com.projects.SalesSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.SalesSystem.entities.Withdraw;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Long>{

}
