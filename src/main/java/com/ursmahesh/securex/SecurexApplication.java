package com.ursmahesh.securex;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class SecurexApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurexApplication.class, args);
	}

	@Bean
	public CommandLineRunner checkSequence(JdbcTemplate jdbcTemplate) {
		return args -> {
			// Verify the sequence exists
			try {
				Integer nextVal = jdbcTemplate.queryForObject(
					"SELECT nextval('providers_id_seq')", Integer.class);
				System.out.println("✅ Sequence providers_id_seq is working. Next value: " + nextVal);
			} catch (Exception e) {
				System.err.println("❌ Error with sequence: " + e.getMessage());
				// Create the sequence if it doesn't exist
				try {
					jdbcTemplate.execute("CREATE SEQUENCE IF NOT EXISTS providers_id_seq START 1");
					System.out.println("✅ Created sequence providers_id_seq");
				} catch (Exception ex) {
					System.err.println("❌ Failed to create sequence: " + ex.getMessage());
				}
			}

			// Verify the provider_id column settings
			try {
				String result = jdbcTemplate.queryForObject(
					"SELECT column_default FROM information_schema.columns " +
					"WHERE table_name = 'providers' AND column_name = 'provider_id'", String.class);
				System.out.println("✅ provider_id column default: " + result);
			} catch (Exception e) {
				System.err.println("❌ Error checking provider_id column: " + e.getMessage());
			}
		};
	}
}
