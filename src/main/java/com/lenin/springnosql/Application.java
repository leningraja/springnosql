package com.lenin.springnosql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import com.mongodb.ConnectionString;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Application {

	public static void main(String[] args) {
		/*
		 * SpringApplication application = new SpringApplication(Application.class);
		 * application.setAdditionalProfiles("local"); application.run(args);
		 */

		SpringApplication.run(Application.class, args);
	}
	
	@Bean
    public MongoClientSettingsBuilderCustomizer customizer(@Value("${spring.data.mongodb.uri}") String uri) {
		log.info("MongoDB URI:{}", uri);
        ConnectionString connection = new ConnectionString(uri);
        return settings -> settings.applyConnectionString(connection);
    }

	/*
	 * @Bean public MongoClientFactoryBean
	 * mongo(@Value("${spring.data.mongodb.uri}") String uri) throws Exception {
	 * 
	 * log.info("MongoDB URI:{}", uri); MongoClientFactoryBean mongo = new
	 * MongoClientFactoryBean(); ConnectionString conn = new ConnectionString(uri);
	 * mongo.setConnectionString(conn); mongo.setSingleton(false);
	 * 
	 * MongoClient client = mongo.getObject();
	 * client.listDatabaseNames().forEach(log::info); return mongo; }
	 */

}
