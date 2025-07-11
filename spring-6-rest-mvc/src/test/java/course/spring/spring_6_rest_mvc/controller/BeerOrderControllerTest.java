package course.spring.spring_6_rest_mvc.controller;

import course.spring.spring_6_rest_mvc.repositories.BeerOrderRepository;
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

import static course.spring.spring_6_rest_mvc.controller.BeerControllerTest.jwtRequestPostProcessor;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;

@SpringBootTest
@ActiveProfiles("test")
class BeerOrderControllerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    BeerOrderRepository beerOrderRepository;

    MockMvc mockMvc;

    @BeforeEach
    void setUP(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
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