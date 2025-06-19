package guru.spring_reactive_mongodb.mappers;


import guru.spring_reactive_mongodb.domain.Beer;
import guru.spring_reactive_mongodb.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BeerMapper {

    BeerDTO toDTO(Beer beer);

    Beer toEntity(BeerDTO beerDTO);
}
