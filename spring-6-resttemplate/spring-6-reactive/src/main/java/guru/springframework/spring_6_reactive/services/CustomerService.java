package guru.springframework.spring_6_reactive.services;

import guru.springframework.spring_6_reactive.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Flux<CustomerDTO> listCustomers();

    Mono<CustomerDTO> getCustomerById(Integer id);

    Mono<CustomerDTO> saveNewBeer(CustomerDTO customerDTO);

    Mono<CustomerDTO> updateCustomer(Integer customerId,CustomerDTO customerDTO);

    Mono<CustomerDTO> patchCustomer(Integer customerId,CustomerDTO customerDTO);

    Mono<Void> deleteCustomerById(Integer customerId);



}
