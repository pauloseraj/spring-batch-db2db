package demo.configuration;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DatasourceConfiguration {

	@Primary
	 @Bean(name = "sourceDb")
	 @ConfigurationProperties(prefix = "spring.datasource")
	 public DataSource sourceDataSource() {
	  return DataSourceBuilder.create().build();
	 }
	
	
	 @Bean(name = "destinationDb")
	 @ConfigurationProperties(prefix = "spring.batch.datasource")
	 public DataSource destinationDataSource() {
	  return DataSourceBuilder.create().build();
	 }
}
