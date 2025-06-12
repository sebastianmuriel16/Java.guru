package guru.springframework.spring_6_reactive.mappers;

import guru.springframework.spring_6_reactive.domain.Beer;
import guru.springframework.spring_6_reactive.model.BeerDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface BeerMapper {

    Beer toEntity(BeerDTO beerDTO);

    BeerDTO toDTO(Beer beer);

}
