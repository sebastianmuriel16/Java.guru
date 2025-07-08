package course.spring.spring_6_rest_mvc.services;

import course.spring.spring_6_rest_mvc.mappers.CustomerMapper;
import course.spring.spring_6_rest_mvc.model.BeerDTO;
import course.spring.spring_6_rest_mvc.model.CustomerDTO;
import course.spring.spring_6_rest_mvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final CacheManager cacheManager;

    @Cacheable(cacheNames = "customerListCache")
    @Override
    public List<CustomerDTO> listCustomers() {
        cacheManager.getCache("customerListCache").clear();
        log.info("customer list - in service");

        return customerRepository.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "customerCache")
    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        log.info("get customer - in service");
        return Optional.ofNullable(customerMapper.customerToCustomerDto(customerRepository.findById(id).orElse(null)));
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customer) {
        cacheManager.getCache("customerListCache").clear();
        return customerMapper.customerToCustomerDto(customerRepository.save(customerMapper.customerDtoToCustomer(customer)));

    }

    @Override
    public Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customer) {
        clearCache(id);

        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();
        customerRepository.findById(id).ifPresentOrElse(foundCustomer->{

            foundCustomer.setCustomerName(customer.getCustomerName());
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(foundCustomer))));

        }, ()->{
            atomicReference.set(Optional.empty());
        });
        return atomicReference.get();
    }


    @Override
    public Boolean deleteCustomerByid(UUID id) {
        clearCache(id);

        if(customerRepository.existsById(id)){
            customerRepository.deleteById(id);
            return true;
        }
        return false;
    }
    private void clearCache(UUID customerId){
        cacheManager.getCache("customerCache").evict(customerId);
        cacheManager.getCache("customerListCache").clear();
    }

    @Override
    public Optional<CustomerDTO> patchCustomerById(UUID id, CustomerDTO customer) {
        clearCache(id);

        AtomicReference<Optional<CustomerDTO>> atomicReference = new AtomicReference<>();

        customerRepository.findById(id).ifPresentOrElse(foundCustomer ->{
            if(StringUtils.hasText(customer.getCustomerName())){
                foundCustomer.setCustomerName(customer.getCustomerName());
            }
            atomicReference.set(Optional.of(customerMapper.customerToCustomerDto(customerRepository.save(foundCustomer))));
        }, () ->{
            atomicReference.set(Optional.empty());
        } );
        return atomicReference.get();
    }
}
