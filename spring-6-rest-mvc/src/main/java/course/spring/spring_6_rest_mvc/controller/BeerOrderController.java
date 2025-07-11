package course.spring.spring_6_rest_mvc.controller;

import course.spring.spring_6_rest_mvc.model.BeerOrderDTO;
import course.spring.spring_6_rest_mvc.services.BeerOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
public class BeerOrderController {

    public static final String BEER_ORDER_PATH = "/api/v1/beerorder";
    public static final String BEER_ORDER_PATH_ID = BEER_ORDER_PATH + "/{beerOrderId}";
    private final BeerOrderService beerOrderService;

    @GetMapping(BEER_ORDER_PATH)
    public Page<BeerOrderDTO> listBeerOrders(@RequestParam(required = false) Integer pageNumber,
                                             @RequestParam(required = false) Integer pageSize)
    {
        return beerOrderService.listOrders(pageNumber,pageSize);
    }

    @GetMapping(BEER_ORDER_PATH_ID)
    public BeerOrderDTO getBeerOrderById(@PathVariable("beerOrderId") UUID beerOrderId){

        return beerOrderService.getBeerOrderById(beerOrderId).orElseThrow(NotFoundException::new);
    }



}
