package guru.springframework.spring_6_reactive.controllers;


import guru.springframework.spring_6_reactive.model.BeerDTO;
import guru.springframework.spring_6_reactive.repositories.BeerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;



@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(5)
    void testDeleteBeer(){
        webTestClient.delete().uri(BeerController.BEER_PATH_ID,1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(4)
    void testUpdateBeer() {

        webTestClient.put().uri(BeerController.BEER_PATH_ID,1)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(3)
    void testCreateBeer() {

        webTestClient.post().uri(BeerController.BEER_PATH)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/beer/5");
    }

    @Test
    @Order(2)
    void testBeerById(){
        webTestClient.get().uri(BeerController.BEER_PATH_ID,1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody(BeerDTO.class);
    }

    @Test
    @Order(1)
    void testListBeers(){
        webTestClient.get().uri(BeerController.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(4);
    }

}