package course.guru.springframework.spring_6_di;

import course.guru.springframework.spring_6_di.controllers.MyControllers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
public class Spring6DiApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Spring6DiApplication.class, args);

        MyControllers controller = ctx.getBean(MyControllers.class);

        System.out.println("In Main method");

        System.out.println(controller.sayHello());

    }

}
