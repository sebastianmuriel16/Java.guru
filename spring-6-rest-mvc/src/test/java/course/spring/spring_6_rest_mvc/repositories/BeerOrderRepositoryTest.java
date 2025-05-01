package course.spring.spring_6_rest_mvc.repositories;

import course.spring.spring_6_rest_mvc.entities.Beer;
import course.spring.spring_6_rest_mvc.entities.BeerOrder;
import course.spring.spring_6_rest_mvc.entities.BeerOrderShipment;
import course.spring.spring_6_rest_mvc.entities.Customer;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BeerRepository beerRepository;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setUp(){
        testCustomer = customerRepository.findAll().get(0);
        testBeer = beerRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void testBeerOrders(){
        BeerOrder beerOrder = BeerOrder.builder()
                .customerRef("test Customer")
                .beerOrderShipment(BeerOrderShipment.builder()
                        .trackingNumber("12345r")
                        .build())
                .customer(testCustomer)
                .build();

        BeerOrder savedBeerOrder = beerOrderRepository.save(beerOrder);
        System.out.println(savedBeerOrder.getCustomerRef());
    }

}