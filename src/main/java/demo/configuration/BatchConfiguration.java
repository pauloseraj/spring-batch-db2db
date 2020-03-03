package demo.configuration;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import demo.mapper.PersonRowMapper;
import demo.model.Person;
import demo.processor.PersonItemProcessor;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
	
	@Autowired
	private JobRegistry jobRegistry;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JobBuilderFactory jobs;
	
	@Autowired
	private StepBuilderFactory steps;

	
	@Bean
	public Job importUserJob(final Step s1) {
		return jobs.get("importUserJob").incrementer(new RunIdIncrementer()).flow(s1).end().build();
	}
	
	

	
	
	@Bean
	public Step step1(final ItemStreamReader<Person> reader,
            final ItemWriter<Person> writer) {
		return steps.get("databaseToDatabaseStep")
				.<Person, Person> chunk(10)
				.reader(reader)
				.processor(processor())
				.writer(writer)
				.build();
	}
	
	
	@Bean
	public ItemStreamReader<Person> reader(@Qualifier("sourceDb") final DataSource dataSource) {
		JdbcCursorItemReader<Person> reader = new JdbcCursorItemReader<Person>();
		reader.setDataSource(dataSource);
			reader.setSql("SELECT first_name, last_name FROM dbo.MOCK_DATA_PERSON");
		reader.setRowMapper(new PersonRowMapper());
		return reader;
	}

	@Bean
	public ItemProcessor<Person, Person> processor() {
		return new PersonItemProcessor();
	}

	@Bean
	public ItemWriter<Person> writer(@Qualifier("destinationDb") final DataSource dataSource) {
		JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
		writer.setSql("INSERT INTO DBO.MOCK_DATA_EMPLOYEE1 (first_name, last_name) VALUES (:firstName, :lastName)");
		writer.setDataSource(dataSource);
		return writer;
	}
	
}
