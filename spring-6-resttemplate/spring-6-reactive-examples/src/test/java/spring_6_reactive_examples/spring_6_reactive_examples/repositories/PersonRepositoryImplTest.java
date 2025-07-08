package spring_6_reactive_examples.spring_6_reactive_examples.repositories;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import spring_6_reactive_examples.spring_6_reactive_examples.domain.Person;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {

    PersonRepository personRepository = new PersonRepositoryImpl();

    @Test
    void testGetByIdFound(){
        Mono<Person> personMono = personRepository.findById(3);
        assertTrue(personMono.hasElement().block());
    }

    @Test
    void testGetByIdNotFound(){
        Mono<Person> personMono = personRepository.findById(6);
        assertFalse(personMono.hasElement().block());
    }

    @Test
    void testGetByIdFoundStepverifier(){
        Mono<Person> personMono = personRepository.findById(3);

        StepVerifier.create(personMono).expectNextCount(1).verifyComplete();

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testGetByIdNotFoundStepverifier(){
        Mono<Person> personMono = personRepository.findById(8);

        StepVerifier.create(personMono).expectNextCount(0).verifyComplete();

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testMonoByIdBlock(){ //manera bloqueante de obtener un Mono
        Mono<Person> personMono = personRepository.getById(1);

        Person person = personMono.block();

        System.out.println(person.toString());
    }


    @Test
    void testGetByIdSuscriber(){ // manera no bloqueante
        Mono<Person> personMono = personRepository.getById(1);

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testMapOperation(){
        Mono<Person> personMono = personRepository.getById(1);

        personMono.map(Person::getFirstName).subscribe(firstName ->{
            System.out.println(firstName);
        });
    }


    @Test
    void testFluxBlockFirst(){
        Flux<Person> personFlux = personRepository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person.toString());

    }

    @Test
    void testFluxMap(){
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.map(Person::getFirstName).subscribe(firstName -> System.out.println(firstName));

    }

    @Test
    void testFluxToList(){
        Flux<Person> personFlux = personRepository.findAll();

        Mono<List<Person>> listMono = personFlux.collectList();

        listMono.subscribe(list -> {
            list.forEach(person -> System.out.println(person.getFirstName()));
        });
    }

    @Test
    void testFilterOnName(){
        personRepository.findAll()
                .filter(person -> person.getFirstName().equals("Jim"))
                .subscribe(person -> System.out.println(person));
    }

    @Test
    void testGetById(){
        Mono<Person> jimMono = personRepository.findAll()
                .filter(person -> person.getFirstName().equals("Jim"))
                .next();

        jimMono.subscribe(person -> System.out.println(person.getFirstName()));
    }

    @Test
    void testFindPersonByIdNotFound(){
        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 5;

        Mono<Person> personMono = personFlux.filter(person -> person.getId() == id).single();

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        }, throwable -> {
            System.out.println("Error ocurred in the mono");
            System.out.println(throwable.toString());
        });
    }

}