package guru.spring_reactive_mongodb.repositories;

import guru.spring_reactive_mongodb.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveMongoRepository<Customer,String> {

    Flux<Customer> findByCustomerNameContaining(String customerName);
}
