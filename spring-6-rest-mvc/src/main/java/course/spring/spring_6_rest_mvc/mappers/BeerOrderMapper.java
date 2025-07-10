package course.spring.spring_6_rest_mvc.mappers;


import course.spring.spring_6_rest_mvc.entities.BeerOrder;
import course.spring.spring_6_rest_mvc.entities.BeerOrderLine;
import course.spring.spring_6_rest_mvc.entities.BeerOrderShipment;
import course.spring.spring_6_rest_mvc.model.BeerOrderDTO;
import course.spring.spring_6_rest_mvc.model.BeerOrderLineDTO;
import course.spring.spring_6_rest_mvc.model.BeerOrderShipmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BeerOrderMapper {

    @Mapping(target = "beerOrder",ignore = true)
    BeerOrderShipment beerOrderShipmentDtoToBeerOrderShipement(BeerOrderShipmentDTO beerOrderShipmentDTO);

    BeerOrderShipmentDTO beerOrderShipmentToBeerShipmentDto(BeerOrderShipment beerOrderShipment);

    @Mapping(target = "beerOrder",ignore = true)
    BeerOrderLine beerOrderLineDtoToBeerOrderLine(BeerOrderLineDTO beerOrderLineDTO);

    BeerOrderLineDTO beerOrderLineToBeerOrderLineDTO(BeerOrderLine beerOrderLine);

    BeerOrder beerOrderDtoToBeerOrder(BeerOrderDTO beerOrderDTO);

    BeerOrderDTO beerOrderToBeerOrder(BeerOrder beerOrder);

}
