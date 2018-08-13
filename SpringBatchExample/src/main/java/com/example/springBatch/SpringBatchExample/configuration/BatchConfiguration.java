package com.example.springBatch.SpringBatchExample.configuration;

import javax.persistence.EntityManagerFactory;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.example.springBatch.SpringBatchExample.model.entity.Person;
import com.example.springBatch.SpringBatchExample.model.entity.PersonFieldSetMapper;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

	// Logs

	@Autowired
	EntityManagerFactory entityManagerFactory;

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Bean
	public ItemReader<Person> itemReader() {
		FlatFileItemReader<Person> reader = new FlatFileItemReader<>();
		reader.setResource(new ClassPathResource("data.csv"));
		reader.setLinesToSkip(1);
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		DefaultLineMapper lineMapper = new DefaultLineMapper<Person>();
		tokenizer.setNames("user_id", "firstName", "lastName", "modified_date");
		lineMapper.setFieldSetMapper(new PersonFieldSetMapper());
		lineMapper.setLineTokenizer(tokenizer);
		reader.setLineMapper(lineMapper);
		return reader;
	}

	@Bean
	public JpaItemWriter<Person> itemWriter() {
		JpaItemWriter writer = new JpaItemWriter();
		writer.setEntityManagerFactory(entityManagerFactory);
		return writer;
	}

	@Bean
	public ItemProcessor<Person, Person> processor() {
		return new PersonItemProcessor();
	}

	@Bean
	public Job importPersonJob(JobNotificationListener listener, Step step1) {
		return jobBuilderFactory.get("importPersonJob").listener(listener).flow(step1).end().build();
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1").<Person, Person>chunk(10).reader(itemReader()).processor(processor())
				.writer(itemWriter()).build();
	}
}
