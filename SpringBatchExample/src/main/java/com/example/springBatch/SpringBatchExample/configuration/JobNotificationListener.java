package com.example.springBatch.SpringBatchExample.configuration;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.springBatch.SpringBatchExample.model.entity.Person;
import com.example.springBatch.SpringBatchExample.repository.PersonRespository;

@Component
public class JobNotificationListener implements JobExecutionListener {
	@Autowired
	PersonRespository personRepository;

	@Override
	public void afterJob(JobExecution jobExecution) {

		if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
			LocalTime time = LocalTime.now();
			System.out.println("Job completed " + time);
			List<Person> persons = personRepository.findAll();
			for (Person person : persons) {
				System.out.println("Found" + person.getName());
			}
		}
	}

	@Override
	public void beforeJob(JobExecution arg0) {
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now();
		System.out.println("Job started " + time);
	}

}
