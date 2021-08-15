package com.projects.SalesSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.projects.SalesSystem.entities.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long>{

	@Transactional(readOnly = true)
	Person findByCpfCnpj(String cpfCnpj);
}
