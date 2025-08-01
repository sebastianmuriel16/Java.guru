package course.spring.spring_6_rest_mvc.repositories;

import course.spring.spring_6_rest_mvc.entities.Beer;
import guru.springframework.spring6restmvcapi.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.test.context.ActiveProfiles;


import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;


import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//@Import({BoostrapData.class, BeerCSVRecord.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("localmysql")
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;


    @Test
    void testGetBeerListByName(){
        Page<Beer> list = beerRepository.findByBeerNameContainingIgnoreCase("IPA", null);

        assertThat(list.getContent().size()).isEqualTo(337);
    }

    @Test
    void testGetBeerListByNameAndBeerStyle(){
        Page<Beer> list = beerRepository.findByBeerNameContainingIgnoreCaseAndBeerStyle("IPA", BeerStyle.IPA, null);

        assertThat(list.getContent().size()).isEqualTo(311);
    }

    @Test
    void testSaveBeerNameTooLong() {

        assertThrows(ConstraintViolationException.class, ()->{
            Beer savedBeer = beerRepository.save(Beer.builder()
                    .beerName("My new Beer name is to long how pass the validations lalalalaalalalalalaalalalalaallalalala")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("221153")
                    .price(new BigDecimal(109))
                    .build());

            beerRepository.flush();
        });

    }

    @Test
    void testSaveBeer() {
        Beer savedBeer = beerRepository.save(Beer.builder()
                        .beerName("My new Beer")
                        .beerStyle(BeerStyle.PALE_ALE)
                        .upc("221153")
                        .price(new BigDecimal(109))
                .build());

        beerRepository.flush();
        assertThat(savedBeer).isNotNull();
        assertThat(savedBeer.getId()).isNotNull();
    }

}