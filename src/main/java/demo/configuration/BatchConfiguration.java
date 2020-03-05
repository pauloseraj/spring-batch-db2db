package demo.configuration;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import demo.mapper.PersonRowMapper;
import demo.model.Employee;
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
	public Step step1(final ItemReader<Person> reader, final ItemWriter<Employee> writer,
			 ItemProcessor<Person, Employee> processor) {
		return steps.get("databaseToDatabaseStep").<Person, Employee>chunk(10).reader(reader).processor(processor())
				.writer(writer).build();
	}
	
	

	@Bean
	public ItemProcessor<Person, Person> processorName() {
		return new ValidationProcessor();
	}

//	@Bean
//	public ItemStreamReader<Person> reader(@Qualifier("sourceDb") final DataSource dataSource) {
//		JdbcCursorItemReader<Person> reader = new JdbcCursorItemReader<Person>();
//		reader.setDataSource(dataSource);
//		reader.setSql("SELECT first_name, last_name FROM dbo.MOCK_DATA_PERSON");
//		reader.setRowMapper(new PersonRowMapper());
//		return reader;
//	}

	@Bean
	public ItemProcessor<Person, Employee> processor() {
		return new PersonItemProcessor();
	}

//	@Bean
//	public ItemWriter<Person> writer(@Qualifier("destinationDb") final DataSource dataSource) {
//		JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
//		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
//		writer.setSql("INSERT INTO DBO.MOCK_DATA_EMPLOYEE (first_name, last_name) VALUES (:firstName, :lastName)");
//		writer.setDataSource(dataSource);
//		return writer;
//	}
	
	
	
	
	 @Bean
	 public ItemReader<Person> reader(@Qualifier("sourceDb") final DataSource dataSource) throws Exception {
	        String jpqlQuery = "select a from Person a";

	    		JpaPagingItemReader<Person> reader = new JpaPagingItemReader<Person>();
	    		reader.setQueryString(jpqlQuery);
	    		reader.setEntityManagerFactory(entityManagerFactory(dataSource).getObject());
	    		reader.setPageSize(10);
	    		reader.afterPropertiesSet();
	    		reader.setSaveState(true);

	    		return reader;
	    }
	
	@Bean
    public ItemWriter<Employee> writer(@Qualifier("destinationDb") final DataSource dataSource) {
        JpaItemWriter writer = new JpaItemWriter<Person>();
        writer.setEntityManagerFactory(entityManagerFactory(dataSource).getObject());
        return writer;
    }
	
	
//	 @Bean(name="step2")
//	 public ItemReader<Person> readerStep2(@Qualifier("destinationDb") final DataSource dataSource) throws Exception {
//	        String jpqlQuery = "select a from Employee a";
//
//	    		JpaPagingItemReader<Person> reader = new JpaPagingItemReader<Person>();
//	    		reader.setQueryString(jpqlQuery);
//	    		reader.setEntityManagerFactory(entityManagerFactory(dataSource).getObject());
//	    		reader.setPageSize(10);
//	    		reader.afterPropertiesSet();
//	    		reader.setSaveState(true);
//
//	    		return reader;
//	    }
	
	
		@Bean
	    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
	
	        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
	        lef.setPackagesToScan("demo");
	        lef.setDataSource(dataSource);
	        lef.setJpaVendorAdapter(jpaVendorAdapter());
	        lef.setJpaProperties(new Properties());
	        return lef;
	    }
		
		@Bean
	    public JpaVendorAdapter jpaVendorAdapter() {
	        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
	        jpaVendorAdapter.setDatabase(Database.SQL_SERVER);
	        jpaVendorAdapter.setGenerateDdl(true);
	        jpaVendorAdapter.setShowSql(false);

	        jpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.SQLServer2012Dialect");
	        return jpaVendorAdapter;
	    }
	
//	@Bean
//	public ItemWriter<Person> writerSource(@Qualifier("sourceDb") final DataSource dataSource) {
//		JdbcBatchItemWriter<Person> writer = new JdbcBatchItemWriter<Person>();
//		writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Person>());
//		writer.setSql("UPDATE DBO.MOCK_DATA_PERSON SET processed = '1' WHERE UPPER(first_name) = UPPER(:firstName)");
//		writer.setDataSource(dataSource);
//		return writer;
//	}

}
