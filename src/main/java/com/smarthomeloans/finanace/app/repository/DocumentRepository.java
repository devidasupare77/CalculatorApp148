package com.smarthomeloans.finanace.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.smarthomeloans.finanace.app.model.Documents;

@Repository
public interface DocumentRepository extends JpaRepository<Documents,Integer> {

}
