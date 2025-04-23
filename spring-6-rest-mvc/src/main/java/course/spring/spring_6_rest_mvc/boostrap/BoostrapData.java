package course.spring.spring_6_rest_mvc.boostrap;

import course.spring.spring_6_rest_mvc.entities.Beer;
import course.spring.spring_6_rest_mvc.entities.Customer;
import course.spring.spring_6_rest_mvc.model.BeerCSVRecord;
import course.spring.spring_6_rest_mvc.model.BeerStyle;
import course.spring.spring_6_rest_mvc.repositories.BeerRepository;
import course.spring.spring_6_rest_mvc.repositories.CustomerRepository;
import course.spring.spring_6_rest_mvc.services.BeerCsvService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


@Component
@RequiredArgsConstructor
public class BoostrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;
    private final BeerCsvService beerCsvService;

    @Transactional
    @Override
    public void run(String... args) throws Exception {

        loadBeerData();
        loadCsvData();
        LoadCustomerData();

    }

    private void loadCsvData() throws FileNotFoundException {
        if(beerRepository.count() < 10){
            File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");

            List<BeerCSVRecord> recs = beerCsvService.convertCSV(file);

            recs.forEach(beerCSVRecord -> {
                BeerStyle beerStyle = switch (beerCSVRecord.getStyle()) {
                    case "American Pale Lager" -> BeerStyle.LAGER;
                    case "American Pale Ale (APA)", "American Black Ale", "Belgian Dark Ale", "American Blonde Ale" ->
                            BeerStyle.ALE;
                    case "American IPA", "American Double / Imperial IPA", "Belgian IPA" -> BeerStyle.IPA;
                    case "American Porter" -> BeerStyle.PORTER;
                    case "Oatmeal Stout", "American Stout" -> BeerStyle.STOUT;
                    case "Saison / Farmhouse Ale" -> BeerStyle.SAISON;
                    case "Fruit / Vegetable Beer", "Winter Warmer", "Berliner Weissbier" -> BeerStyle.WHEAT;
                    case "English Pale Ale" -> BeerStyle.PALE_ALE;
                    default -> BeerStyle.PILSNER;
                };

                beerRepository.save(Beer.builder()
                        .beerName(StringUtils.abbreviate(beerCSVRecord.getBeer(),50))
                        .beerStyle(beerStyle)
                        .price(BigDecimal.TEN)
                        .upc(beerCSVRecord.getRow().toString())
                        .quantityOnHand(beerCSVRecord.getCount())
                        .build());

        });
    }


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
