package guru.springframework.spring6resttemplate.client;

import com.fasterxml.jackson.databind.JsonNode;
import guru.springframework.spring6resttemplate.model.BeerDTO;
import guru.springframework.spring6resttemplate.model.BeerDTOPageImpl;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BeerClientImpl implements BeerClient {

    private final RestTemplateBuilder restTemplateBuilder;
    public static final String GET_BEER_PATH = "/api/v1/beer";
    public static final String GET_BEER_BY_ID = "/api/v1/beer/{beerId}";


    @Override
    public void deleteBeer(UUID beerId){
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete(GET_BEER_BY_ID,beerId);
    }

    @Override
    public BeerDTO updateBeer(BeerDTO beerDTO){

        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.put(GET_BEER_BY_ID,beerDTO,beerDTO.getId());

        return getBeerById(beerDTO.getId());
    }



    @Override
    public BeerDTO createBeer(BeerDTO newDTO){
        RestTemplate restTemplate = restTemplateBuilder.build();

        URI uri = restTemplate.postForLocation(GET_BEER_PATH,newDTO);

        return restTemplate.getForObject(uri.getPath(),BeerDTO.class);
    }

    @Override
    public BeerDTO getBeerById(UUID beerId) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        return restTemplate.getForObject(GET_BEER_BY_ID,BeerDTO.class, beerId);
    }

    @Override
    public Page<BeerDTO> listBeers(){
        return this.listBeers(null, null, null, null, null);
    }

    @Override
    public Page<BeerDTO> listBeers(String beerName,String beerStyle,
                                   Boolean showInventory,Integer pageNumber,Integer pageSize) {

        RestTemplate restTemplate = restTemplateBuilder.build();

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromPath(GET_BEER_PATH);

        if(beerName != null){
            uriComponentsBuilder.queryParam("beerName",beerName);
        }
        if(beerStyle != null){
            uriComponentsBuilder.queryParam("beerStyle",beerStyle);
        }
        if(showInventory != null){
            uriComponentsBuilder.queryParam("showInventory",showInventory);
        }
        if (pageNumber != null){
            uriComponentsBuilder.queryParam("pageNumber",pageNumber);
        }
        if(pageSize != null){
            uriComponentsBuilder.queryParam("pageSize",pageSize);
        }

        ResponseEntity<BeerDTOPageImpl> response =
                restTemplate.getForEntity(uriComponentsBuilder.toUriString(), BeerDTOPageImpl.class);

//        ResponseEntity<Map> mapResponse =
//                restTemplate.getForEntity(BASE_URL + GET_BEER_PATH, Map.class);
//
//        ResponseEntity<JsonNode> jsonResponse =
//                restTemplate.getForEntity(BASE_URL + GET_BEER_PATH, JsonNode.class);
//
//        jsonResponse.getBody().findPath("content")
//                        .elements().forEachRemaining(node ->{
//                        System.out.println(node.get("beerName").asText());
//                });

    //    System.out.println(stringResponse.getBody());
        return response.getBody();
    }
}
