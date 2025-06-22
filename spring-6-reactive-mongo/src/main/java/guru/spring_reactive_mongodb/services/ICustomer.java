package guru.spring_reactive_mongodb.services;

import guru.spring_reactive_mongodb.model.CustomerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ICustomer {

    Flux<CustomerDTO> findByCustomerName(String customerName);

    Flux<CustomerDTO> listCustomers();

    Mono<CustomerDTO> saveCustomer(Mono<CustomerDTO> customerDTO);

    Mono<CustomerDTO> getById(String customerId);

    Mono<CustomerDTO> updateCustomer(String customerId,CustomerDTO customerDTO);

    Mono<CustomerDTO> patchCustomer(String customerId,CustomerDTO customerDTO);

    Mono<Void> deleteCustomerById(String customerId);
}
