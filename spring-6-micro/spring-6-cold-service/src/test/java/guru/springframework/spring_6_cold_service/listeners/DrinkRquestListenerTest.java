package guru.springframework.spring_6_cold_service.listeners;

import guru.springframework.spring6restmvcapi.events.DrinkRequestEvent;
import guru.springframework.spring6restmvcapi.model.BeerDTO;
import guru.springframework.spring6restmvcapi.model.BeerOrderLineDTO;
import guru.springframework.spring6restmvcapi.model.BeerStyle;

import guru.springframework.spring_6_cold_service.config.KafkaConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@EmbeddedKafka(controlledShutdown = true,topics = {KafkaConfig.DRINK_REQUEST_COLD_TOPIC}, partitions = 1)
class DrinkRquestListenerTest {

    @Autowired
    DrinkRquestListener drinkRquestListener;

    @Autowired
    DrinkPreparedListener drinkPreparedListener;

    @Test
    void listenDrinksRequest(){
        drinkRquestListener.listenDrinkRequest(DrinkRequestEvent.builder()
                        .beerOrderLineDTO(createDTO())
                .build());

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(()-> {
            assertEquals(1,drinkPreparedListener.iceColdMessageCounter.get());
        });
    }

    public BeerOrderLineDTO createDTO(){
        return BeerOrderLineDTO.builder()
                .beer(BeerDTO.builder()
                        .id(UUID.randomUUID())
                        .beerStyle(BeerStyle.IPA)
                        .beerName("test Beer")
                        .build())
                .build();
    }
}