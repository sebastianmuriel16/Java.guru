package course.spring.spring_6_rest_mvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import course.spring.spring_6_rest_mvc.model.*;
import course.spring.spring_6_rest_mvc.repositories.BeerOrderRepository;
import course.spring.spring_6_rest_mvc.repositories.BeerRepository;
import course.spring.spring_6_rest_mvc.repositories.CustomerRepository;
import jakarta.transaction.Transactional;
import lombok.Builder;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.Set;

import static course.spring.spring_6_rest_mvc.controller.BeerControllerTest.jwtRequestPostProcessor;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
class BeerOrderControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    BeerOrderRepository beerOrderRepository;


    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    ObjectMapper objectMapper;

    MockMvc mockMvc;

    @BeforeEach
    void setUP(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
    }

    @Test
    void testDelete() throws Exception{
        val beerOrder = beerOrderRepository.findAll().get(0);

        mockMvc.perform(delete(BeerOrderController.BEER_ORDER_PATH_ID,beerOrder.getId())
                .with(jwtRequestPostProcessor))
                .andExpect(status().isNoContent());

        assertTrue(beerOrderRepository.findById(beerOrder.getId()).isEmpty());

        mockMvc.perform(delete(BeerOrderController.BEER_ORDER_PATH_ID,beerOrder.getId())
                        .with(jwtRequestPostProcessor))
                .andExpect(status().isNotFound());
    }

    @Transactional
    @Test
    void testUpdateOrder() throws Exception{
        val beerOrder = beerOrderRepository.findAll().get(0);

        Set<BeerOrderLineUpdateDTO> lines = new HashSet<>();

        beerOrder.getBeerOrderLines().forEach(beerOrderLine -> {
            lines.add(BeerOrderLineUpdateDTO.builder()
                    .id(beerOrderLine.getId())
                    .beerId(beerOrderLine.getBeer().getId())
                    .orderQuantity(beerOrderLine.getOrderQuantity())
                    .quantityAllocated(beerOrderLine.getQuantityAllocated())
                    .build());
        });

        val beerOrderUpdateDTO = BeerOrderUpdateDTO.builder()
                .customerId(beerOrder.getCustomer().getId())
                .customerRef("TestRef")
                .beerOrderLines(lines)
                .beerOrderShipment(BeerOrderShipmentUpdateDTO.builder()
                        .trackingNumber("123456")
                        .build())
                .build();

        mockMvc.perform(put(BeerOrderController.BEER_ORDER_PATH_ID, beerOrder.getId())
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(beerOrderUpdateDTO))
                .with(jwtRequestPostProcessor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerRef", is("TestRef")));
    }

    @Test
    void testCreateBeerOrder() throws Exception{

        val customer = customerRepository.findAll().get(0);
        val beer = beerRepository.findAll().get(0);

        val beerOrderCreateDTO = BeerOrderCreateDTO.builder()
                .customerId(customer.getId())
                .beerOrderLines(Set.of(BeerOrderLineCreateDTO.builder()
                        .beerId(beer.getId())
                        .orderQuantity(1)
                        .build()))
                .build();

        mockMvc.perform(post(BeerOrderController.BEER_ORDER_PATH)
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(beerOrderCreateDTO))
                .with(jwtRequestPostProcessor))
                .andExpect(status().isCreated())
                .andExpect(header().exists("location"));

    }

    @Test
    void testListBeerOrsers() throws Exception{
        mockMvc.perform(get(BeerOrderController.BEER_ORDER_PATH)
                .with(jwtRequestPostProcessor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.size()", greaterThan(0)));
    }

    @Test
    void testGetBeerOrderById() throws Exception{
        val beerOrder = beerOrderRepository.findAll().get(0);

        mockMvc.perform(get(BeerOrderController.BEER_ORDER_PATH_ID, beerOrder.getId())
                .with(jwtRequestPostProcessor))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(beerOrder.getId().toString())));
    }

}