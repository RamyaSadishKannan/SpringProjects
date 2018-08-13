package com.example.springBatch.SpringBatchExample.configuration;

import org.springframework.batch.item.ItemProcessor;

import com.example.springBatch.SpringBatchExample.model.entity.Person;

public class PersonItemProcessor implements ItemProcessor<Person, Person> {

	@Override
	public Person process(Person person) throws Exception {
		// TODO Auto-generated method stub
		return person;
	}

}
