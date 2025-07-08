package course.spring.spring_6_rest_mvc.mappers;

import course.spring.spring_6_rest_mvc.entities.Beer;
import course.spring.spring_6_rest_mvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);

}
