package guru.springframework.spring_6_reactive.controllers;


import guru.springframework.spring_6_reactive.domain.Beer;
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

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(99)
    void testDeleteBeer(){
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(BeerController.BEER_PATH_ID,1)
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    void testDeleteBeerNotFound(){
        webTestClient
                .mutateWith(mockOAuth2Login())
                .delete()
                .uri(BeerController.BEER_PATH_ID,99)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(10)
    void testPatchBeerNotFound(){
        webTestClient
                .mutateWith(mockOAuth2Login())
                .patch().uri(BeerController.BEER_PATH_ID,43)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()),BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(7)
    void testUpadteBeerBadData() {

        Beer beer = BeerRepositoryTest.getTestBeer();
        beer.setBeerName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerController.BEER_PATH_ID,1)
                .body(Mono.just(beer), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(9)
    void testUpadteBeerNotFound() {

        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerController.BEER_PATH_ID,99)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(4)
    void testUpdateBeer() {

        webTestClient
                .mutateWith(mockOAuth2Login())
                .put()
                .uri(BeerController.BEER_PATH_ID,1)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(6)
    void testCreateBeerBadData() {

        Beer beer = BeerRepositoryTest.getTestBeer();
        beer.setBeerName("");

        webTestClient
                .mutateWith(mockOAuth2Login())
                .post()
                .uri(BeerController.BEER_PATH)
                .body(Mono.just(beer), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(3)
    void testCreateBeer() {

        webTestClient
                .mutateWith(mockOAuth2Login())
                .post()
                .uri(BeerController.BEER_PATH)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/beer/6");
    }

    @Test
    @Order(2)
    void testBeerById(){
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(BeerController.BEER_PATH_ID,1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody(BeerDTO.class);
    }

    @Test
    @Order(8)
    void testBeerByIdNotFound(){
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(BeerController.BEER_PATH_ID,999)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(1)
    void testListBeers(){
        webTestClient
                .mutateWith(mockOAuth2Login())
                .get()
                .uri(BeerController.BEER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(5);
    }

}