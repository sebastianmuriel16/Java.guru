package guru.springframework.spring_6_reactive.mappers;

import guru.springframework.spring_6_reactive.domain.Customer;
import guru.springframework.spring_6_reactive.model.CustomerDTO;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO toDto(Customer customer);

    Customer toEntity(CustomerDTO customerDTO);

}
