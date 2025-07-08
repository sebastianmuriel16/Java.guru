package course.spring.spring_6_rest_mvc.mappers;


import course.spring.spring_6_rest_mvc.entities.Customer;
import course.spring.spring_6_rest_mvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    CustomerDTO customerToCustomerDto(Customer customer);
}
