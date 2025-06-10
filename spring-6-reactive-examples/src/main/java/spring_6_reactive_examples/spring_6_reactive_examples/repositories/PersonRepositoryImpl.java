package spring_6_reactive_examples.spring_6_reactive_examples.repositories;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import spring_6_reactive_examples.spring_6_reactive_examples.domain.Person;

@RequiredArgsConstructor
public class PersonRepositoryImpl implements PersonRepository {

    Person michael = Person.builder()
            .id(1)
            .firstName("Michael")
            .lastName("Scott")
            .build();
    Person pam = Person.builder()
            .id(2)
            .firstName("Pam")
            .lastName("Beesly")
            .build();
    Person jim = Person.builder()
            .id(3)
            .firstName("Jim")
            .lastName("Halpert")
            .build();

    @Override
    public Mono<Person> getById(Integer id) {
        return Mono.just(michael);
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(michael, pam, jim);
    }

    @Override
    public Mono<Person> findById(final Integer id){
        Flux<Person> personFlux = findAll();

        Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).next();

        return  personMono;
    }
}
