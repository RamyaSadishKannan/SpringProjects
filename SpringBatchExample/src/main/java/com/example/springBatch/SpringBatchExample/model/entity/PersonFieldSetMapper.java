package com.example.springBatch.SpringBatchExample.model.entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class PersonFieldSetMapper implements FieldSetMapper<Person> {

	@Override
	public Person mapFieldSet(FieldSet fieldSet) throws BindException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		Person person = new Person();
		person.setId(fieldSet.readInt(0));
		person.setFirstName(fieldSet.readString(1));
		person.setLastName(fieldSet.readString(2));
		person.concatenateName();
		String date = fieldSet.readString(3);
		try {
			person.setDob(dateFormat.parse(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return person;
	}

}
