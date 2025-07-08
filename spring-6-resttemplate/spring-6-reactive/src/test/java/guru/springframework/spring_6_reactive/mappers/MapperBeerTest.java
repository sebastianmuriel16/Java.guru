package guru.springframework.spring_6_reactive.mappers;

import guru.springframework.spring_6_reactive.domain.Beer;
import guru.springframework.spring_6_reactive.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
public class MapperBeerTest {

    @Autowired
    BeerMapper beerMapper;

    @Test
    void testMapper() {
        Beer beer = Beer.builder()
                .id(1)
                .beerName("Pale Ale")
                .beerStyle("Ale")
                .upc("123456789")
                .quantityOnHand(10)
                .price(new BigDecimal("9.99"))
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        BeerDTO dto = beerMapper.toDTO(beer);
        System.out.println(dto); // Todos los campos deberían estar presentes
    }
}
