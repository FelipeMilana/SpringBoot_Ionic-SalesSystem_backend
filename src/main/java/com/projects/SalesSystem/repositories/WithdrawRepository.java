package com.projects.SalesSystem.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projects.SalesSystem.entities.Withdraw;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Long>{

	@Transactional(readOnly = true)
	Page<Withdraw> findByIdIn(List<Long> withdrawIds, Pageable page);
}
