package com.smarthomeloans.finanace.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarthomeloans.finanace.app.model.CustomerRegistration;


@Repository
public interface RelationshipExecutiveRepository extends JpaRepository<CustomerRegistration, Integer>{

	Optional<CustomerRegistration> findByCustomerName(String customerName);

	List<CustomerRegistration> findByCibilStatus(String cibilStatus);

	



}
