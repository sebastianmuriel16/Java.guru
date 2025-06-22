package guru.spring_reactive_mongodb.services;

import guru.spring_reactive_mongodb.domain.Customer;
import guru.spring_reactive_mongodb.mappers.CustomerMapper;
import guru.spring_reactive_mongodb.model.CustomerDTO;
import guru.spring_reactive_mongodb.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ICustomerImpl implements ICustomer {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Flux<CustomerDTO> findByCustomerName(String customerName) {
        return customerRepository.findByCustomerNameContaining(customerName)
                .map(customerMapper::toDTO);
    }

    @Override
    public Flux<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .map(customerMapper::toDTO);
    }

    @Override
    public Mono<CustomerDTO> saveCustomer(Mono<CustomerDTO> customerDTO) {
        return customerDTO.map(customerMapper::toEntity)
                .flatMap(customerRepository::save)
                .map(customerMapper::toDTO);
    }

    @Override
    public Mono<CustomerDTO> getById(String customerId) {
        return customerRepository.findById(customerId)
                .map(customerMapper::toDTO);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(String customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(foundCustomer ->{
                    foundCustomer.setCustomerName(customerDTO.getCustomerName());
                    return foundCustomer;
                })
                .flatMap(customerRepository::save)
                .map(customerMapper::toDTO);
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(String customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(foundCustomer ->{
                    if(StringUtils.hasText(customerDTO.getCustomerName())){
                        foundCustomer.setCustomerName(customerDTO.getCustomerName());
                    }
                    return foundCustomer;
                })
                .flatMap(customerRepository::save)
                .map(customerMapper::toDTO);
    }

    @Override
    public Mono<Void> deleteCustomerById(String customerId) {
        return customerRepository.deleteById(customerId);
    }
}
