package guru.springframework.spring_6_reactive.controllers;

import guru.springframework.spring_6_reactive.domain.Customer;
import guru.springframework.spring_6_reactive.model.BeerDTO;
import guru.springframework.spring_6_reactive.model.CustomerDTO;
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

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class CustomerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(1)
    void testListCustomers(){
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(4);
    }

    @Test
    @Order(2)
    void testBeerById(){
        webTestClient.get().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type","application/json")
                .expectBody(BeerDTO.class);

    }

    @Test
    @Order(3)
    void testCreateCustomer(){

        Customer customer = Customer.builder()
                .customerName("sebastian")
                        .build();

        webTestClient.post().uri(CustomerController.CUSTOMER_PATH)
                .body(Mono.just(customer), CustomerDTO.class)
                .header("Content-type","application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/customer/5");

    }

    @Test
    @Order(4)
    void testUpdateBeer(){

        Customer customer = Customer.builder()
                .customerName("sebastian").build();

        webTestClient.put().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .body(Mono.just(customer), CustomerDTO.class)
                .header("Content-Type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(5)
    void testDelete(){
        webTestClient.delete().uri(CustomerController.CUSTOMER_PATH_ID,1)
                .exchange()
                .expectStatus().isNoContent();
    }

}