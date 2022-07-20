package ua.jupiter;

import io.sentry.Sentry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.envers.repository.config.EnableEnversRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;



@EnableJpaAuditing
@EnableEnversRepositories
@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		Sentry.capture("Application started");
		SpringApplication.run(Application.class, args);
	}
}
