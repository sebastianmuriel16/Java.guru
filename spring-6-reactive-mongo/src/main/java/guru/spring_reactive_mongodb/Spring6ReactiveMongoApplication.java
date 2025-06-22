package guru.spring_reactive_mongodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
public class Spring6ReactiveMongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring6ReactiveMongoApplication.class, args);
	}

}
