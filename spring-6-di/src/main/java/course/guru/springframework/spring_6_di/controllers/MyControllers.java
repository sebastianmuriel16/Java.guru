package course.guru.springframework.spring_6_di.controllers;

import course.guru.springframework.spring_6_di.services.GreetingService;
import course.guru.springframework.spring_6_di.services.GreetingServiceImpl;
import org.springframework.stereotype.Controller;

@Controller
public class MyControllers {

    private final GreetingService greetingService;

    public MyControllers(){
        this.greetingService = new GreetingServiceImpl();
    }

    public String sayHello() {
        System.out.println("I'm in the controller");
        return greetingService.sayGreeting();
    }

}
