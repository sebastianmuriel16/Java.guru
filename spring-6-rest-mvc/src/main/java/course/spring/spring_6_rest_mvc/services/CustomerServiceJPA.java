package course.spring.spring_6_rest_mvc.services;

import course.spring.spring_6_rest_mvc.mappers.CustomerMapper;
import course.spring.spring_6_rest_mvc.model.CustomerDTO;
import course.spring.spring_6_rest_mvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> listCustomers() {
        return List.of();
    }

    @Override
    public CustomerDTO getCustomerById(UUID id) {
        return null;
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public void updateCustomerById(UUID id, CustomerDTO customer) {

    }

    @Override
    public void deleteCustomerByid(UUID id) {

    }

    @Override
    public void patchCustomerById(UUID id, CustomerDTO customer) {

    }
}
