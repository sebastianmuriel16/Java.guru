package course.spring.spring_6_rest_mvc.services;

import course.spring.spring_6_rest_mvc.entities.BeerOrder;
import course.spring.spring_6_rest_mvc.model.BeerOrderCreateDTO;
import course.spring.spring_6_rest_mvc.model.BeerOrderDTO;
import course.spring.spring_6_rest_mvc.model.BeerOrderUpdateDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface BeerOrderService {

    Page<BeerOrderDTO> listOrders(Integer pageNumber, Integer pageSize);

    Optional<BeerOrderDTO> getBeerOrderById(UUID beerOrderId);

    BeerOrder createOrder(BeerOrderCreateDTO beerOrderCreateDTO);

    BeerOrderDTO updateOrder(UUID beerOrderId, BeerOrderUpdateDTO beerOrderUpdateDTO);


    void deleteOrder(UUID beerOrderId);
}
