package course.spring.spring_6_rest_mvc.controller;

import course.spring.spring_6_rest_mvc.model.BeerDTO;
import course.spring.spring_6_rest_mvc.model.BeerStyle;
import course.spring.spring_6_rest_mvc.services.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";


    private final BeerService beerService;

    @PatchMapping(BEER_PATH_ID)
    public ResponseEntity patchById(@PathVariable("beerId") UUID beerId,@RequestBody BeerDTO beer){
        beerService.patchBeerById(beerId,beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(BEER_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("beerId") UUID beerId){

        if(!beerService.deleteBeerById(beerId)){
            throw new NotFoundException();
        };

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PutMapping(BEER_PATH_ID)
    public ResponseEntity updateById(@PathVariable("beerId") UUID beerId,@Validated @RequestBody BeerDTO beer){
        if( beerService.updateBeerById(beerId,beer).isEmpty()){
            throw new NotFoundException();
        };

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = BEER_PATH)
    public Page<BeerDTO> listBeers(@RequestParam(required = false) String beerName,
                                   @RequestParam(required = false) BeerStyle beerStyle,
                                   @RequestParam(required = false) Boolean showInventory,
                                   @RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize){
        return beerService.listBeers(beerName,beerStyle, showInventory, pageNumber, pageSize);
    }



    @PostMapping(BEER_PATH)
    public ResponseEntity handlePost(@Validated @RequestBody BeerDTO beer){
        BeerDTO savedBeer = beerService.saveNewBeer(beer);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + savedBeer.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }


    @GetMapping(value = BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable("beerId") UUID beerId){
        log.debug("Get Beer by id in controller");

        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }



}
