package course.spring.spring_6_rest_mvc.services;

import course.spring.spring_6_rest_mvc.model.Customer;
import java.util.List;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomers();

    Customer getCustomerById(UUID id);

    Customer saveNewCustomer(Customer customer);

    void updateCustomerById(UUID id,Customer customer);

    void deleteCustomerByid(UUID id);

    void patchCustomerById(UUID id,Customer customer);
}
