package course.spring.spring_6_rest_mvc.services;

import course.spring.spring_6_rest_mvc.model.CustomerDTO;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> listCustomers();

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveNewCustomer(CustomerDTO customer);

    void updateCustomerById(UUID id, CustomerDTO customer);

    void deleteCustomerByid(UUID id);

    void patchCustomerById(UUID id, CustomerDTO customer);
}
