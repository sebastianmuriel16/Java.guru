package guru.springframework.spring_6_reactive.controllers;

import guru.springframework.spring_6_reactive.model.BeerDTO;
import guru.springframework.spring_6_reactive.services.BeerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class BeerController {

    public static final String BEER_PATH = "/api/v2/beer";
    public static final String BEER_PATH_ID =  BEER_PATH + "/{beerId}";
    private final BeerService beerService;


    @DeleteMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> deleteById(@PathVariable("beerId") Integer beerId){
        return beerService.getBeerById(beerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(beerDTO -> beerService.deleteBeerById(beerDTO.getId()))
                .thenReturn(ResponseEntity.noContent().build());
    }


    @PatchMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> patchExistingBeer(@PathVariable("beerId") Integer beerId,
                                                 @Validated @RequestBody  BeerDTO beerDTO){
        return beerService.patchBeer(beerId,beerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(saveDto -> ResponseEntity.ok().build());
    }


    @PutMapping(BEER_PATH_ID)
    Mono<ResponseEntity<Void>> updateExisting(@PathVariable("beerId") Integer beerId,
                                              @Validated @RequestBody BeerDTO beerDTO){

        return beerService.updateBeer(beerId,beerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(savedDto -> ResponseEntity.noContent().build());
    }

    @PostMapping(BEER_PATH)
    Mono<ResponseEntity<Void>> createNewBeer(@Validated @RequestBody BeerDTO beerDTO){
       return beerService.saveNewBeer(beerDTO)
                .map(savedDto ->{
                    URI location = UriComponentsBuilder
                            .fromUriString("http://localhost:8080" + BEER_PATH + "/{id}")
                            .buildAndExpand(savedDto.getId())
                            .toUri();

                    return ResponseEntity.created(location).build();

                        });
    }


    @GetMapping(BEER_PATH_ID)
    Mono<BeerDTO> getBeerById(@PathVariable("beerId") Integer id){
        return beerService.getBeerById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @GetMapping(BEER_PATH)
    Flux<BeerDTO> listBeers(){
        return beerService.listBeers();
    }

}
