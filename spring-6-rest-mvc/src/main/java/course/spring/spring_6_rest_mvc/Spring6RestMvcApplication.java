package course.spring.spring_6_rest_mvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@EnableCaching
@SpringBootApplication
@ComponentScan(basePackages = "course.spring.spring_6_rest_mvc")
public class Spring6RestMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring6RestMvcApplication.class, args);
	}

}
