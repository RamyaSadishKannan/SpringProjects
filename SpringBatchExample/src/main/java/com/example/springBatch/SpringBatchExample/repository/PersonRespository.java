package com.example.springBatch.SpringBatchExample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springBatch.SpringBatchExample.model.entity.Person;

@Repository
public interface PersonRespository extends JpaRepository<Person, Long> {

}
