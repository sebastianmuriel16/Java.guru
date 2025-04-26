package course.spring.spring_6_rest_mvc.services;

import java.util.Optional;
import java.util.UUID;
import course.spring.spring_6_rest_mvc.model.BeerDTO;
import course.spring.spring_6_rest_mvc.model.BeerStyle;
import org.springframework.data.domain.Page;

public interface BeerService {

    Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize);

    Optional<BeerDTO> getBeerById(UUID id);

    BeerDTO saveNewBeer(BeerDTO beer);

    Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer);

    Boolean deleteBeerById(UUID beerId);

    Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer);
}
