package guru.springframework.spring_6_reactive.services;

import guru.springframework.spring_6_reactive.mappers.CustomerMapper;
import guru.springframework.spring_6_reactive.model.CustomerDTO;
import guru.springframework.spring_6_reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public Flux<CustomerDTO> listCustomers() {
        return customerRepository.findAll()
                .map(customerMapper::toDto);
    }

    @Override
    public Mono<CustomerDTO> getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .map(customerMapper::toDto);
    }

    @Override
    public Mono<CustomerDTO> saveNewBeer(CustomerDTO customerDTO) {
        return customerRepository.save(customerMapper.toEntity(customerDTO))
                .map(customerMapper::toDto);
    }

    @Override
    public Mono<CustomerDTO> updateCustomer(Integer customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId).
                map(foundCustomer -> {
                    foundCustomer.setCustomerName(customerDTO.getCustomerName());
                    return foundCustomer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::toDto);
    }

    @Override
    public Mono<CustomerDTO> patchCustomer(Integer customerId, CustomerDTO customerDTO) {
        return customerRepository.findById(customerId)
                .map(foundCustomer ->{
                    if(StringUtils.hasText(customerDTO.getCustomerName())){
                        foundCustomer.setCustomerName(customerDTO.getCustomerName());
                    }
                    return foundCustomer;
                }).flatMap(customerRepository::save)
                .map(customerMapper::toDto);

    }

    @Override
    public Mono<Void> deleteCustomerById(Integer customerId) {
        return customerRepository.deleteById(customerId);
    }
}
