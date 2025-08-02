package guru.springframework.spring_6_cold_service.listeners;

import guru.springframework.spring6restmvcapi.events.DrinkPreparedEvent;
import guru.springframework.spring6restmvcapi.events.DrinkRequestEvent;
import guru.springframework.spring_6_cold_service.config.KafkaConfig;
import guru.springframework.spring_6_cold_service.services.DrinkRequestProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class DrinkRquestListener {

    private final DrinkRequestProcessor drinkRequestProcessor;
    private final KafkaTemplate<String,Object> kafkaTemplate;

    @KafkaListener(groupId = "ColdListener",topics = KafkaConfig.DRINK_REQUEST_COLD_TOPIC)
    public void listenDrinkRequest(DrinkRequestEvent event){
        log.debug("I am listening - drink request");

        drinkRequestProcessor.processDrinkRequest(event);

        kafkaTemplate.send(KafkaConfig.DRINK_PREPARED_TOPIC, DrinkPreparedEvent.builder()
                .beerOrderLine(event.getBeerOrderLineDTO())
                .build());

    }

}
