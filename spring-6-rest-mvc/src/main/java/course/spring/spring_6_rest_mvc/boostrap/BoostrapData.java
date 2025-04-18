package course.spring.spring_6_rest_mvc.boostrap;

import course.spring.spring_6_rest_mvc.entities.Beer;
import course.spring.spring_6_rest_mvc.entities.Customer;
import course.spring.spring_6_rest_mvc.model.BeerStyle;
import course.spring.spring_6_rest_mvc.repositories.BeerRepository;
import course.spring.spring_6_rest_mvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;


@Component
@RequiredArgsConstructor
public class BoostrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {

        loadBeerData();
        LoadCustomerData();

    }

    private void LoadCustomerData() {

        if(beerRepository.count() == 0){
            Beer beer1 = Beer.builder()
                    .beerName("Galaxy Cat")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("223432")
                    .price(new BigDecimal("12.99"))
                    .quantityOnHand(122)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer2 = Beer.builder()
                    .beerName("Hazy IPA")
                    .beerStyle(BeerStyle.IPA)
                    .upc("223433")
                    .price(new BigDecimal("14.50"))
                    .quantityOnHand(200)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Beer beer3 = Beer.builder()
                    .beerName("Mango Tango")
                    .beerStyle(BeerStyle.PALE_ALE)
                    .upc("223434")
                    .price(new BigDecimal("10.99"))
                    .quantityOnHand(150)
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            beerRepository.save(beer1);
            beerRepository.save(beer2);
            beerRepository.save(beer3);

        }


    }

    private void loadBeerData() {

        if(customerRepository.count() == 0){

            Customer customer1 = Customer.builder()
                    .customerName("John Doe")
                    .createdDate(LocalDateTime.now())
                    .updateDate(LocalDateTime.now())
                    .build();

            Customer customer2 = Customer.builder()
                    .customerName("Jane Smith")
                    .createdDate(LocalDateTime.now().minusDays(10))
                    .updateDate(LocalDateTime.now().minusDays(5))
                    .build();

            Customer customer3 = Customer.builder()
                    .customerName("Alice Johnson")
                    .createdDate(LocalDateTime.now().minusMonths(2))
                    .updateDate(LocalDateTime.now().minusDays(2))
                    .build();

            customerRepository.saveAll(Arrays.asList(customer1,customer2,customer3));
        }

    }
}
