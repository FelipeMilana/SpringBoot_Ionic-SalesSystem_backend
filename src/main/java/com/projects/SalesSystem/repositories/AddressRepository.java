package com.projects.SalesSystem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.projects.SalesSystem.entities.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long>{

}
