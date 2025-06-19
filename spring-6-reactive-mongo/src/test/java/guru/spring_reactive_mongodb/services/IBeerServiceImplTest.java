package guru.spring_reactive_mongodb.services;

import guru.spring_reactive_mongodb.domain.Beer;
import guru.spring_reactive_mongodb.mappers.BeerMapper;
import guru.spring_reactive_mongodb.mappers.BeerMapperImpl;
import guru.spring_reactive_mongodb.model.BeerDTO;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.stereotype.Service;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.testcontainers.junit.jupiter.Container;
import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;


@Testcontainers
@SpringBootTest
class IBeerServiceImplTest {

    @Container
    @ServiceConnection
    public static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    IBeerService beerService;

    @Autowired
    BeerMapper beerMapper;

    BeerDTO beerDTO;
    @BeforeEach
    void setUp(){
        beerDTO = beerMapper.toDTO(getTestbeer());
    }

    @Test
    void testFindByBeerStyle(){
        BeerDTO beerDTO1 = getSavedBeerDto();
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        beerService.findByBeerStyle(beerDTO1.getBeerStyle())
                .subscribe(dto ->{
                    System.out.println(dto.toString());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);

    }


    @Test
    void findFirstByBeerName(){

        BeerDTO beerDto = getSavedBeerDto();

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        Mono<BeerDTO> foundDto = beerService.findFirstByBeerName(beerDto.getBeerName());

        foundDto.subscribe(dto ->{
            System.out.println(dto.toString());
            atomicBoolean.set(true);
        });

        await().untilTrue(atomicBoolean);
    }

    @Test
    void saveBeerUseSusbcriber() {

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);
        AtomicReference<BeerDTO> atomicDto = new AtomicReference<>();

        Mono<BeerDTO> savedMono = beerService.saveBeer(Mono.just(beerDTO));

        savedMono.subscribe(savedDto -> {
            System.out.println(savedDto.getId());
            atomicBoolean.set(true);
            atomicDto.set(savedDto);
        });

        await().untilTrue(atomicBoolean);
        BeerDTO persistedDto = atomicDto.get();
        assertThat(persistedDto).isNotNull();
        assertThat(persistedDto.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test Save Beer Using Block")
    void testSaveBeerUseBlock(){
        BeerDTO savedDto = beerService.saveBeer(Mono.just(getTestBeerDto())).block();
        assertThat(savedDto).isNotNull();
        assertThat(savedDto.getId()).isNotNull();
    }

    @Test
    @DisplayName("Test Update Beer Using Block")
    void testUpdateBlocking(){
        final String newName = "New Beer Name";
        BeerDTO savedBeerDto = getSavedBeerDto();
        savedBeerDto.setBeerName(newName);

        BeerDTO updatedDto = beerService.saveBeer(Mono.just(savedBeerDto)).block();

        //verify exists in db
        BeerDTO fetchedDto = beerService.getById(updatedDto.getId()).block();
        assertThat(fetchedDto.getBeerName()).isEqualTo(newName);
    }

    public BeerDTO getSavedBeerDto(){
        return beerService.saveBeer(Mono.just(getTestBeerDto())).block();
    }

    @Test
    @DisplayName("Test Update Using Reactive Streams")
    void testUpdateStreaming(){
        final String newName = "New Beer Name";

        AtomicReference<BeerDTO> atomicDto = new AtomicReference<>();

        beerService.saveBeer(Mono.just(getTestBeerDto()))
                .map(savedBeerDto -> {
                    savedBeerDto.setBeerName(newName);
                    return savedBeerDto;
                })
                .flatMap(beerService::saveBeer)
                .flatMap(saveUpdateDto -> beerService.getById(saveUpdateDto.getId()))
                .subscribe(dtoFromDB ->{
                    atomicDto.set(dtoFromDB);
                });
        await().until(() -> atomicDto.get() != null);
        assertThat(atomicDto.get().getBeerName()).isEqualTo(newName);

    }

    @Test
    void testDeleteBeer() {
        BeerDTO beerToDelete = getSavedBeerDto();

        beerService.deleteBeerById(beerToDelete.getId()).block();

        Mono<BeerDTO> expectedEmptyBeerMono = beerService.getById(beerToDelete.getId());

        BeerDTO emptyBeer = expectedEmptyBeerMono.block();

        assertThat(emptyBeer).isNull();

    }

    public static BeerDTO getTestBeerDto(){
        return new BeerMapperImpl().toDTO(getTestbeer());
    }

    public static Beer getTestbeer(){
        return Beer.builder()
                .beerName("Space Dust")
                .beerStyle("IPA")
                .price(BigDecimal.TEN)
                .upc("1234567")
                .quantityOnHand(123)
                .build();
    }

}