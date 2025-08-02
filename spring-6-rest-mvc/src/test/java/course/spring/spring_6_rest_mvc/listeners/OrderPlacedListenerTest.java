package course.spring.spring_6_rest_mvc.listeners;

import course.spring.spring_6_rest_mvc.config.KafkaConfig;
import guru.springframework.spring6restmvcapi.events.DrinkRequestEvent;
import guru.springframework.spring6restmvcapi.events.OrderPlaceEvent;
import guru.springframework.spring6restmvcapi.model.BeerDTO;
import guru.springframework.spring6restmvcapi.model.BeerOrderDTO;
import guru.springframework.spring6restmvcapi.model.BeerOrderLineDTO;
import guru.springframework.spring6restmvcapi.model.BeerStyle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaListenerEndpointRegistry;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.Matchers.greaterThan;

@ActiveProfiles("test")
@SpringBootTest
@EmbeddedKafka(controlledShutdown = true,topics = {KafkaConfig.ORDER_PLACED_TOPIC}, partitions = 1,kraft = true)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class OrderPlacedListenerTest {

    @Autowired
    KafkaListenerEndpointRegistry kafkaListenerEndpointRegistry;

    @Autowired
    DrinkSplitterRouter drinkSplitter;

    @Autowired
    DrinkListenerKafkaConsumer drinkListenerKafkaConsumer;


    @Autowired
    OrderPlacedListener orderPlacedListener;

    @Autowired
    OrderPlacedKafkaListener orderPlacedKafkaListener;

    @BeforeEach
    void setUp(){
        kafkaListenerEndpointRegistry.getListenerContainers().forEach(container ->{
            ContainerTestUtils.waitForAssignment(container,1);
        });
    }

    @Test
    void listenSplitter()  {
        drinkSplitter.receive(OrderPlaceEvent.builder()
                .beerOrderDTO(buildOrder())
                .build());

        await().atMost(15, TimeUnit.SECONDS).pollDelay(100, TimeUnit.MILLISECONDS)
                .until(drinkListenerKafkaConsumer.iceColdMessageCounter::get, greaterThan(0));

        await().atMost(15, TimeUnit.SECONDS).pollDelay(100, TimeUnit.MILLISECONDS)
                .until(drinkListenerKafkaConsumer.coldMessageCounter::get, greaterThan(0));

        await().atMost(15, TimeUnit.SECONDS).pollDelay(100, TimeUnit.MILLISECONDS)
                .until(drinkListenerKafkaConsumer.coolMessageCounter::get, greaterThan(0));
    }

    @Test
    void listen(){
        OrderPlaceEvent orderPlaceEvent = OrderPlaceEvent.builder().beerOrderDTO(BeerOrderDTO.builder()
                        .id(UUID.randomUUID())
                .build()).build();

        orderPlacedListener.listen(orderPlaceEvent);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            assertEquals(1,orderPlacedKafkaListener.messageCounter.get());
        });
    }

    BeerOrderDTO buildOrder() {

        Set<BeerOrderLineDTO> beerOrderLines = new HashSet<>();

        beerOrderLines.add(BeerOrderLineDTO.builder()
                .beer(BeerDTO.builder()
                        .id(UUID.randomUUID())
                        .beerStyle(BeerStyle.IPA)
                        .beerName("Test Beer")
                        .build())
                .build());

        //add lager
        beerOrderLines.add(BeerOrderLineDTO.builder()
                .beer(BeerDTO.builder()
                        .id(UUID.randomUUID())
                        .beerStyle(BeerStyle.LAGER)
                        .beerName("Test Beer")
                        .build())
                .build());

        //add gose
        beerOrderLines.add(BeerOrderLineDTO.builder()
                .beer(BeerDTO.builder()
                        .id(UUID.randomUUID())
                        .beerStyle(BeerStyle.GOSE)
                        .beerName("Test Beer")
                        .build())
                .build());

        return BeerOrderDTO.builder()
                .id(UUID.randomUUID())
                .beerOrderLines(beerOrderLines)
                .build();
    }
}