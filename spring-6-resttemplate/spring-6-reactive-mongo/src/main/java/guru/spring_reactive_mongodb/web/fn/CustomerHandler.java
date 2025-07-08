package guru.spring_reactive_mongodb.web.fn;

import guru.spring_reactive_mongodb.model.BeerDTO;
import guru.spring_reactive_mongodb.model.CustomerDTO;
import guru.spring_reactive_mongodb.services.ICustomer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class CustomerHandler {


    public final ICustomer customerService;
    private final Validator validator;

    private void validate(CustomerDTO customerDTO){
        Errors errors = new BeanPropertyBindingResult(customerDTO,"customerDto");
        validator.validate(customerDTO,errors);

        if(errors.hasErrors()){
            throw new ServerWebInputException(errors.toString());
        }
    }

    public Mono<ServerResponse> listCustomers(ServerRequest request){
        Flux<CustomerDTO> flux;
        if(request.queryParam("customerName").isPresent()){
            flux= customerService.findByCustomerName(request.queryParam("customerName").get());
        }
        else {
            flux = customerService.listCustomers();
        }
        return ServerResponse.ok()
                .body(flux,CustomerDTO.class);

    }

    public Mono<ServerResponse> getCustomerById(ServerRequest request){
        return ServerResponse
                .ok()
                .body(customerService.getById(request.pathVariable("customerId"))
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                ,CustomerDTO.class);
    }

    public Mono<ServerResponse> createNewCustomer(ServerRequest request){
        return customerService.saveCustomer(request.bodyToMono(CustomerDTO.class)
                .doOnNext(this::validate))
                .flatMap(customerDTO -> ServerResponse
                        .created(UriComponentsBuilder
                                .fromPath(CustomerRouterConfig.CUSTOMER_PATH_ID)
                                .build(customerDTO.getId()))
                        .contentType(MediaType.APPLICATION_JSON)
                                .bodyValue(customerDTO)
                        );
    }

    public Mono<ServerResponse> updateCustomerById(ServerRequest request){
        return request.bodyToMono(CustomerDTO.class)
                .doOnNext(this::validate)
                .flatMap(customerDTO -> customerService
                        .updateCustomer(request.pathVariable("customerId"),customerDTO))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(updatedCustomer -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(updatedCustomer)
                );
    }

    public Mono<ServerResponse> patchCustomerById(ServerRequest request){
        return request.bodyToMono(CustomerDTO.class)
                .doOnNext(this::validate)
                .flatMap(customerDTO -> customerService
                        .patchCustomer(request.pathVariable("customerId"),customerDTO))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(patchCustomer -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(patchCustomer)
                );
    }

    public Mono<ServerResponse> deleteBeer(ServerRequest request){
        return customerService.getById(request.pathVariable("customerId"))
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)))
                .flatMap(customerDTO -> customerService.deleteCustomerById(customerDTO.getId()))
                .then(ServerResponse.noContent().build());
    }


}
