package course.spring.spring_6_rest_mvc.listeners;

import course.spring.spring_6_rest_mvc.config.KafkaConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

@Component
public class DrinkListenerKafkaConsumer {

    AtomicInteger iceColdMessageCounter = new AtomicInteger(0);
    AtomicInteger coldMessageCounter = new AtomicInteger(0);
    AtomicInteger coolMessageCounter = new AtomicInteger(0);

    @KafkaListener(groupId = "KafkaIntegrationTest", topics = KafkaConfig.DRINK_REQUEST_ICE_COLD_TOPIC)
    public void listenIceCode() {
        System.out.println("I am listening - ice cold");
        iceColdMessageCounter.incrementAndGet();
    }

    //list cold beer
    @KafkaListener(groupId = "KafkaIntegrationTest", topics = KafkaConfig.DRINK_REQUEST_COLD_TOPIC)
    public void listenCold() {
        System.out.println("I am listening - cold");
        coldMessageCounter.incrementAndGet();
    }

    //listen cool beer
    @KafkaListener(groupId = "KafkaIntegrationTest", topics = KafkaConfig.DRINK_REQUEST_COOL_TOPIC)
    public void listenCool() {
        System.out.println("I am listening - cool");
        coolMessageCounter.incrementAndGet();
    }


}
