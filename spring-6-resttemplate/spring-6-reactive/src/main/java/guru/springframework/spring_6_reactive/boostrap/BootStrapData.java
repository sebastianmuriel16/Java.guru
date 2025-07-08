package guru.springframework.spring_6_reactive.boostrap;

import guru.springframework.spring_6_reactive.domain.Beer;
import guru.springframework.spring_6_reactive.domain.Customer;
import guru.springframework.spring_6_reactive.repositories.BeerRepository;
import guru.springframework.spring_6_reactive.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;


@RequiredArgsConstructor
@Component
public class BootStrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();

        beerRepository.count().subscribe(count ->{
            System.out.println("Count is: " + count);
        });
        customerRepository.count().subscribe(
                count ->{
                    System.out.println("Count is: " + count);
                }
        );
    }

    private void loadBeerData() {
        beerRepository.count().subscribe(count -> {
            if (count == 0) {
                Beer beer1 = Beer.builder()
                        .beerName("Galaxy Cat")
                        .beerStyle("PALE ALE")
                        .upc("223432")
                        .price(new BigDecimal("12.99"))
                        .quantityOnHand(122)
                        .createdDate(LocalDate.now())
                        .lastModifiedDate(LocalDate.now())
                        .build();

                Beer beer2 = Beer.builder()
                        .beerName("Hazy IPA")
                        .beerStyle("IPA")
                        .upc("223433")
                        .price(new BigDecimal("14.50"))
                        .quantityOnHand(200)
                        .createdDate(LocalDate.now())
                        .lastModifiedDate(LocalDate.now())
                        .build();

                Beer beer3 = Beer.builder()
                        .beerName("Mango Tango")
                        .beerStyle("PALE ALE")
                        .upc("223434")
                        .price(new BigDecimal("10.99"))
                        .quantityOnHand(150)
                        .createdDate(LocalDate.now())
                        .lastModifiedDate(LocalDate.now())
                        .build();
                    Beer beer4 = Beer.builder()
                            .beerName("Galaxy avengers")
                            .beerStyle("PALE ALE")
                            .upc("223434111")
                            .price(new BigDecimal("16.99"))
                            .quantityOnHand(170)
                            .createdDate(LocalDate.now())
                            .lastModifiedDate(LocalDate.now())
                            .build();
                Beer beer5 = Beer.builder()
                        .beerName("Tropical Storm")
                        .beerStyle("SESSION IPA")
                        .upc("223435")
                        .price(new BigDecimal("11.49"))
                        .quantityOnHand(135)
                        .createdDate(LocalDate.now())
                        .lastModifiedDate(LocalDate.now())
                        .build();

                beerRepository.saveAll(Arrays.asList(beer1, beer2, beer3,beer4,beer5)).subscribe();
            }
        });

    }
    private void loadCustomerData() {
        customerRepository.count().subscribe(count -> {
            if (count == 0) {
                Customer customer1 = Customer.builder()
                        .customerName("John Doe")
                        .createdDate(LocalDate.now())
                        .lastModifiedDate(LocalDate.now())
                        .build();

                Customer customer2 = Customer.builder()
                        .customerName("Jane Smith")
                        .createdDate(LocalDate.now())
                        .lastModifiedDate(LocalDate.now())
                        .build();

                Customer customer3 = Customer.builder()
                        .customerName("Bob Johnson")
                        .createdDate(LocalDate.now())
                        .lastModifiedDate(LocalDate.now())
                        .build();

                Customer customer4 = Customer.builder()
                        .customerName("Alice Walker")
                        .createdDate(LocalDate.now())
                        .lastModifiedDate(LocalDate.now())
                        .build();

                customerRepository.saveAll(Arrays.asList(customer1, customer2, customer3, customer4))
                        .subscribe();
            }
        });
    }


}





