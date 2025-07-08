package guru.spring_reactive_mongodb.mappers;


import guru.spring_reactive_mongodb.domain.Customer;
import guru.spring_reactive_mongodb.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDTO toDTO(Customer customer);

    Customer toEntity(CustomerDTO customerDTO);
}
