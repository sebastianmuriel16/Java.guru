package guru.springframework.spring_6_reactive.controllers;


import guru.springframework.spring_6_reactive.domain.Customer;
import guru.springframework.spring_6_reactive.model.CustomerDTO;
import guru.springframework.spring_6_reactive.services.CustomerService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.Mapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    public static final String CUSTOMER_PATH = "/api/v2/customer";
    public static final String CUSTOMER_PATH_ID =  CUSTOMER_PATH + "/{customerId}";
    private final CustomerService customerService;

    @GetMapping(CUSTOMER_PATH)
    public Flux<CustomerDTO> listCustomers(){
        return customerService.listCustomers();
    }

    @GetMapping(CUSTOMER_PATH_ID)
    public Mono<CustomerDTO> getCustomerById(@PathVariable("customerId") Integer customerId){
        return customerService.getCustomerById(customerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping(CUSTOMER_PATH)
    public Mono<ResponseEntity<Void>> createNewCustomer(@Validated @RequestBody CustomerDTO customerDTO){
        return customerService.saveNewBeer(customerDTO)
                .map(savedDTO ->{
                    URI location = UriComponentsBuilder
                            .fromUriString("http://localhost:8080" + CUSTOMER_PATH + "/{id}")
                            .buildAndExpand(savedDTO.getId())
                            .toUri();
                    return ResponseEntity.created(location).build();
                });
    }

    @PutMapping(CUSTOMER_PATH_ID)
    public Mono<ResponseEntity<Void>> updateCustomerById(@PathVariable("customerId") Integer customerId,@Validated @RequestBody
                                            CustomerDTO customerDTO){

        return customerService.updateCustomer(customerId,customerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(updatedCustomer -> ResponseEntity.noContent().build());

    }

    @PatchMapping(CUSTOMER_PATH_ID)
    public Mono<ResponseEntity<Void>> patchCustomerById(@PathVariable("customerId") Integer customerId,@Validated @RequestBody
                                            CustomerDTO customerDTO){
        return  customerService.patchCustomer(customerId,customerDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(pactchedCustomer -> ResponseEntity.ok().build());
    }

    @DeleteMapping(CUSTOMER_PATH_ID)
    public Mono<ResponseEntity<Void>> deleteCustomerById(@PathVariable("customerId") Integer customerId){
        return customerService.getCustomerById(customerId)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .map(customerDTO -> customerService
                        .deleteCustomerById(customerDTO.getId()))
                .thenReturn(ResponseEntity.noContent().build());
    }



}
