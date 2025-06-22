package guru.spring_reactive_mongodb.bootstrap;


import guru.spring_reactive_mongodb.domain.Beer;
import guru.spring_reactive_mongodb.domain.Customer;
import guru.spring_reactive_mongodb.repositories.BeerRepository;
import guru.spring_reactive_mongodb.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        beerRepository.count()
                .subscribe(count ->{
                    if (count>0){
                        return;
                    }
                    else {
                        loadBeerData();
                    }
                });
        customerRepository.count()
                .subscribe(count -> {
                    if(count>0){
                        return;
                    }
                    else {
                        loadCustomerData();
                    }
                });
    }

    private void loadBeerData() {
    beerRepository.count().subscribe(count ->{

        if (count == 0) {
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle("Pale Ale")
                    .upc("12356")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Crank")
                    .beerStyle("Pale Ale")
                    .upc("12356222")
                    .price(new BigDecimal("11.99"))
                    .quantityOnHand(392)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Sunshine City")
                    .beerStyle("IPA")
                    .upc("12356")
                    .price(new BigDecimal("13.99"))
                    .quantityOnHand(144)
                    .createdDate(LocalDateTime.now())
                    .lastModifiedDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1).subscribe();
            beerRepository.save(beer2).subscribe();
            beerRepository.save(beer3).subscribe();

        }
    });
 };
    private void loadCustomerData() {
        customerRepository.count().subscribe(count -> {

            if (count == 0) {
                Customer customer1 = Customer.builder()
                        .customerName("John Doe")
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Customer customer2 = Customer.builder()
                        .customerName("Jane Smith")
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                Customer customer3 = Customer.builder()
                        .customerName("Alice Johnson")
                        .createdDate(LocalDateTime.now())
                        .lastModifiedDate(LocalDateTime.now())
                        .build();

                customerRepository.save(customer1).subscribe();
                customerRepository.save(customer2).subscribe();
                customerRepository.save(customer3).subscribe();
            }

        });
    }

}
