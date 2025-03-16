package course.spring.spring_6_rest_mvc.boostrap;

import course.spring.spring_6_rest_mvc.repositories.BeerRepository;
import course.spring.spring_6_rest_mvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import static org.assertj.core.api.Assertions.assertThat;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BoostrapDataTest {


    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;


    BoostrapData boostrapData;

    @BeforeEach
    void setUp(){
        boostrapData = new BoostrapData(beerRepository,customerRepository);
    }

    @Test
    void testRun() throws Exception {

        boostrapData.run(null);

        assertThat(beerRepository.count()).isEqualTo(3);
        assertThat(customerRepository.count()).isEqualTo(3);


    }

}