package guru.spring_reactive_mongodb.repositories;

import guru.spring_reactive_mongodb.domain.Customer;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CustomerRepository extends ReactiveMongoRepository<Customer,String> {
}
