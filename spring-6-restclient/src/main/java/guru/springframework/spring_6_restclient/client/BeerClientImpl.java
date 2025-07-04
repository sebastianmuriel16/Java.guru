package guru.springframework.spring_6_restclient.client;


import guru.springframework.spring_6_restclient.model.BeerDTO;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient{

    public static final String GET_BEER_PATH = "/api/v1/beer";
    public static final String GET_BEER_BY_ID = "/api/v1/beer/{beerId}";

    private final RestClient.Builder restClientBuilder;


    @Override
    public BeerDTO updateBeer(BeerDTO beerDTO) {
        return null;
    }

    @Override
    public BeerDTO createBeer(BeerDTO newDTo) {
        RestClient restClient = restClientBuilder.build();

        val location = restClient.post()
                .uri(uriBuilder -> uriBuilder.path(GET_BEER_PATH).build())
                .body(newDTo)
                .retrieve()
                .toBodilessEntity()
                .getHeaders()
                .getLocation();

        return restClient.get()
                .uri(location.getPath())
                .retrieve()
                .body(BeerDTO.class);
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        return null;
    }

    @Override
    public Page<BeerDTO> listBeers() {
        return null;
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName, String beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        return null;
    }

    @Override
    public void deleteBeer(UUID id) {

    }
}
