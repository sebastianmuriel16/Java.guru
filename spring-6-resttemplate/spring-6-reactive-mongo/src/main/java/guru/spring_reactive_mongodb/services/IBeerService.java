package guru.spring_reactive_mongodb.services;

import guru.spring_reactive_mongodb.domain.Beer;
import guru.spring_reactive_mongodb.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBeerService {

    Flux<BeerDTO> findByBeerStyle(String beerStyle);

    Mono<BeerDTO> findFirstByBeerName(String beerName);

    Flux<BeerDTO> listBeers();

    Mono<BeerDTO> saveBeer(BeerDTO beerDTO);

    Mono<BeerDTO> saveBeer(Mono<BeerDTO> beerDTO);

    Mono<BeerDTO> getById(String beerId);

    Mono<BeerDTO> updateBeer(String beerId,BeerDTO beerDTO);

    Mono<BeerDTO> patchBeer(String beerId,BeerDTO beerDTO);

    Mono<Void> deleteBeerById(String beerId);


}
