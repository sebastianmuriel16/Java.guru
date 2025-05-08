package guru.springframework.spring6resttemplate.client;

import guru.springframework.spring6resttemplate.model.BeerDTO;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface BeerClient {

    BeerDTO updateBeer(BeerDTO beerDTO);

    BeerDTO createBeer(BeerDTO newDTo);

    BeerDTO getBeerById(UUID beerId);

    Page<BeerDTO> listBeers();

    Page<BeerDTO> listBeers(String beerName,String beerStyle,
                            Boolean showInventory,Integer pageNumber,Integer pageSize);

    void deleteBeer(UUID id);
}
