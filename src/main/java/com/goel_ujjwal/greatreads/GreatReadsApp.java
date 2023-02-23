package com.goel_ujjwal.greatreads;

import java.nio.file.Path;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import com.goel_ujjwal.greatreads.connection.DataStaxAstraProperties;

// Class with main() for running the Spring Boot application

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
public class GreatReadsApp {

	public static void main(String[] args) {
		SpringApplication.run(GreatReadsApp.class, args);
	}

	// Using secure-bundle to connect to our DataStax Astra Cassandra database  
	@Bean
    public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
        Path bundle = astraProperties.getSecureConnectBundle().toPath();
        return builder -> builder.withCloudSecureConnectBundle(bundle);
    }

}
