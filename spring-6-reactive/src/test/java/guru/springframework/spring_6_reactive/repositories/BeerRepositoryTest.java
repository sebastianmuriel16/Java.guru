package guru.springframework.spring_6_reactive.repositories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring_6_reactive.config.DatabaseConfig;
import guru.springframework.spring_6_reactive.domain.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;


@DataR2dbcTest
@Import(DatabaseConfig.class)
public class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testCreatedJson() throws JsonProcessingException{
        ObjectMapper objectMapper = new ObjectMapper();

        System.out.println(objectMapper.writeValueAsString(getTestBeer()));
    }

    @Test
    void saveNewBeer(){
        beerRepository.save(getTestBeer())
                .subscribe(beer -> {
                    System.out.println(beer);
                });
    }

    public static Beer getTestBeer(){
        return Beer.builder()
                .beerName("spaece t")
                .beerStyle("IPA")
                .price(BigDecimal.TEN)
                .quantityOnHand(23)
                .upc("1234567")
                .build();
    }

}