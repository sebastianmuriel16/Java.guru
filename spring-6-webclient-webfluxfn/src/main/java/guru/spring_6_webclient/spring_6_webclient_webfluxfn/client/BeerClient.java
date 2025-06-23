package guru.spring_6_webclient.spring_6_webclient_webfluxfn.client;

import com.fasterxml.jackson.databind.JsonNode;
import guru.spring_6_webclient.spring_6_webclient_webfluxfn.model.BeerDTO;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.Flow;

public interface BeerClient {

    Flux<String> listBeer();

    Flux<Map> listBeerMap();

    Flux<JsonNode> listBeersJsonNode();

    Flux<BeerDTO> listBeersDtos();
}
