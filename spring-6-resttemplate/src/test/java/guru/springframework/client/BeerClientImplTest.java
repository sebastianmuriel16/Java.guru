package guru.springframework.client;

import guru.springframework.Spring6ResttemplateApplication;
import guru.springframework.spring6resttemplate.client.BeerClientImpl;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerStyle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = Spring6ResttemplateApplication.class)
class BeerClientImplTest {

    @Autowired
    BeerClientImpl beerClient;

    @Test
    void deleteBeer(){
        BeerDTO newDTO = BeerDTO.builder()
                .beerName("Monstad Beer V2")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("9876543")
                .price(new BigDecimal("11.10"))
                .quantityOnHand(224)
                .build();
        BeerDTO beerDTO = beerClient.createBeer(newDTO);

        beerClient.deleteBeer(beerDTO.getId());

        assertThrows(HttpClientErrorException.class, ()->{

            beerClient.getBeerById(beerDTO.getId());
        });
    }
    @Test
    void testUpdateBeer(){
        BeerDTO newDTO = BeerDTO.builder()
                .beerName("Monstad Beer V2")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("9876543")
                .price(new BigDecimal("11.10"))
                .quantityOnHand(224)
                .build();

        BeerDTO beerDTO = beerClient.createBeer(newDTO);

        final String newName = "New Monstad beer";
        beerDTO.setBeerName(newName);
        BeerDTO updatedBeer = beerClient.updateBeer(beerDTO);

        assertEquals(newName, updatedBeer.getBeerName());
    }

    @Test
    void createBeer(){
        BeerDTO newDTO = BeerDTO.builder()
                .beerName("Monstad Beer")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("9876543")
                .price(new BigDecimal("11.10"))
                .quantityOnHand(224)
                .build();
        BeerDTO savedDto = beerClient.createBeer(newDTO);
        assertNotNull(savedDto);
    }

    @Test
    void getBeerById(){
        Page<BeerDTO> beersDTO = beerClient.listBeers();
        BeerDTO dto = beersDTO.getContent().get(0);
        BeerDTO byId = beerClient.getBeerById(dto.getId());

        assertNotNull(byId);
    }

    @Test
    void listBeersNoBeerName(){
        beerClient.listBeers(null,null,null,null,null);
    }

    @Test
    void listBeers(){
        beerClient.listBeers("ALE","IPA",null,null,null);
    }

}