package guru.spring_6_webclient.spring_6_webclient_webfluxfn.client;

import com.fasterxml.jackson.databind.JsonNode;
import guru.spring_6_webclient.spring_6_webclient_webfluxfn.model.BeerDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.util.Map;
import java.util.concurrent.Flow;

@Service
public class BeerClientImpl implements BeerClient {

    private final WebClient webClient;
    public static final String BEER_PATH = "/api/v3/beer";

    public BeerClientImpl(WebClient.Builder webClient ){
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8080").build();
    }

    @Override
    public Flux<BeerDTO> listBeersDtos() {
        return webClient.get().uri(BEER_PATH)
                .retrieve().bodyToFlux(BeerDTO.class);
    }


    @Override
    public Flux<JsonNode> listBeersJsonNode() {
        return webClient.get().uri(BEER_PATH)
                .retrieve().bodyToFlux(JsonNode.class);
    }

    @Override
    public Flux<Map> listBeerMap() {
        return webClient.get().uri(BEER_PATH)
                .retrieve().bodyToFlux(Map.class);
    }

    @Override
    public Flux<String> listBeer() {
        return webClient.get().uri(BEER_PATH)
                .retrieve().bodyToFlux(String.class);
    }
}
