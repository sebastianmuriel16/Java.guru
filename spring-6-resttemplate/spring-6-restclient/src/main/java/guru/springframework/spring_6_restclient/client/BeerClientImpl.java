package guru.springframework.spring_6_restclient.client;


import guru.springframework.spring_6_restclient.model.BeerDTO;
import guru.springframework.spring_6_restclient.model.BeerDTOPageImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient{

    public static final String GET_BEER_PATH = "/api/v1/beer";
    public static final String GET_BEER_BY_ID = "/api/v1/beer/{beerId}";

    private final RestClient.Builder restClientBuilder;



    @Override
    public BeerDTO updateBeer(BeerDTO beerDTO) {
        RestClient restClient = restClientBuilder.build();

        restClient.put()
                .uri(uriBuilder -> uriBuilder.path(GET_BEER_BY_ID).build(beerDTO.getId()))
                .body(beerDTO)
                .retrieve()
                .toBodilessEntity();

        return getBeerById(beerDTO.getId());
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
        RestClient restClient = restClientBuilder.build();

        return restClient.get()
                .uri(uriBuilder -> uriBuilder.path(GET_BEER_BY_ID).build(beerId))
                .retrieve()
                .body(BeerDTO.class);

    }

    @Override
    public Page<BeerDTO> listBeers() {
        return listBeers(null, null, null, null, null);
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName, String beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {

        RestClient restClient = restClientBuilder.build();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);

        if (beerName != null) {
            uriComponentsBuilder.queryParam("beerName", beerName);
        }

        if (beerStyle != null) {
            uriComponentsBuilder.queryParam("beerStyle", beerStyle);
        }

        if (showInventory != null) {
            uriComponentsBuilder.queryParam("showInventory", beerStyle);
        }

        if (pageNumber != null) {
            uriComponentsBuilder.queryParam("pageNumber", beerStyle);
        }

        if (pageSize != null) {
            uriComponentsBuilder.queryParam("pageSize", beerStyle);
        }

        return restClient.get()
                .uri(uriComponentsBuilder.toUriString())
                .retrieve()
                .body(BeerDTOPageImpl.class);
    }

    @Override
    public void deleteBeer(UUID id) {
        RestClient restClient = restClientBuilder.build();

        restClient.delete()
                .uri(uriBuilder -> uriBuilder.path(GET_BEER_BY_ID).build(id))
                .retrieve()
                .toBodilessEntity();

    }
}
