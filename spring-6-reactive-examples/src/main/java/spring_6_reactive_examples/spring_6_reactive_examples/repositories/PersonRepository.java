package spring_6_reactive_examples.spring_6_reactive_examples.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring_6_reactive_examples.spring_6_reactive_examples.domain.Person;

public interface PersonRepository {

    Mono<Person> getById(Integer id);

    Flux<Person> findAll();

    Mono<Person> findById(Integer id);
}
