package course.spring.spring_6_rest_mvc.services;

import course.spring.spring_6_rest_mvc.entities.BeerOrder;
import course.spring.spring_6_rest_mvc.mappers.BeerOrderMapper;
import course.spring.spring_6_rest_mvc.model.BeerOrderDTO;
import course.spring.spring_6_rest_mvc.repositories.BeerOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
@Primary
@RequiredArgsConstructor
public class BeerOrderJPA implements BeerOrderService{

    private final BeerOrderRepository beerOrderRepository;
    private final BeerOrderMapper beerOrderMapper;

    private static final int DEFAULT_PAGE=0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    //metodo auxiliar para paginanacion
    public PageRequest builPageRequest(Integer pageNumber,Integer pageSize){
        int queryPageNumber;
        int queryPageSize;

        if(pageNumber != null && pageNumber > 0){
            queryPageNumber = pageNumber - 1;
        }else {
            queryPageNumber = DEFAULT_PAGE;
        }
        if (pageSize == null){
            queryPageSize = DEFAULT_PAGE_SIZE;
        }else {
            if (pageSize > 20){
                queryPageSize = 20;
            }else {
                queryPageSize = pageSize;
            }
        }

        return PageRequest.of(queryPageNumber,queryPageSize);
    }

    @Override
    public Page<BeerOrderDTO> listOrders(Integer pageNumber, Integer pageSize) {

        PageRequest pageRequest = builPageRequest(pageNumber,pageSize);

        Page<BeerOrder> beerOrderPage;

        beerOrderPage = beerOrderRepository.findAll(pageRequest);

        return beerOrderPage.map(beerOrderMapper::beerOrderToBeerOrderDto);
    }

    @Override
    public Optional<BeerOrderDTO> getBeerOrderById(UUID beerOrderId) {
        return Optional.ofNullable(beerOrderMapper.
                beerOrderToBeerOrderDto(beerOrderRepository.findById(beerOrderId)
                        .orElse(null)));
    }
}
