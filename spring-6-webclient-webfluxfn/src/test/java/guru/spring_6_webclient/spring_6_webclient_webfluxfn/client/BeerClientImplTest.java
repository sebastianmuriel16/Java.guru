package guru.spring_6_webclient.spring_6_webclient_webfluxfn.client;

import guru.spring_6_webclient.spring_6_webclient_webfluxfn.model.BeerDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.awaitility.Awaitility.await;

@SpringBootTest
class BeerClientImplTest {

    @Autowired
    BeerClient client;

    @Test
    void testDelete(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeersDtos()
                .next()
                .flatMap(dto -> client.deleteBeerById(dto))
                .doOnSuccess(mt -> atomicBoolean.set(true))
                .subscribe();

        await().untilTrue(atomicBoolean);
    }

    @Test
    void testpatch() {
        final  String NAME = "New Name";

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeersDtos()
                .next()
                .map(beerDTO -> BeerDTO.builder().beerName(NAME).id(beerDTO.getId()).build())
                .flatMap(dto -> client.patchBeer(dto))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);

    }

    @Test
    void testUpdate() {
        final  String NAME = "New Name";

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeersDtos()
                .next()
                .doOnNext(beerDTO -> beerDTO.setBeerName(NAME))
                .flatMap(dto -> client.updateBeer(dto))
                .subscribe(byIdDto -> {
                    System.out.println(byIdDto.toString());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);

    }

    @Test
    void testCreateBeer(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        BeerDTO newDto = BeerDTO.builder()
                .beerName("three mesli")
                .beerStyle("IPA")
                .quantityOnHand(400)
                .upc("1311355")
                .price(new BigDecimal("11.30"))
                .build();

        client.createBeer(newDto)
                .subscribe(dto -> {
                    System.out.println(dto.toString());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerByStyle(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.getBeerByBeerStyle("Pale Ale")
                .subscribe(dto -> {
                    System.out.println(dto.toString());
                    atomicBoolean.set(true);
                });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void testGetBeerById(){

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeersDtos()
                .flatMap(dto ->client.getBeerById(dto.getId()))
                .subscribe(byIdDto ->{
                    System.out.println(byIdDto.getBeerName());
                    atomicBoolean.set(true);
                });

        await().untilTrue(atomicBoolean);

    }

    @Test
    void testGetBeerDto(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeersDtos().subscribe(dto ->{
            System.out.println(dto.getBeerName());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);

    }

    @Test
    void testGetBeerJson(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeersJsonNode().subscribe(jsonNode ->{
            System.out.println(jsonNode.toPrettyString());
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);

    }

    @Test
    void testGetMap(){
        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeerMap().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);
    }

    @Test
    void listBeer(){

        AtomicBoolean atomicBoolean = new AtomicBoolean(false);

        client.listBeer().subscribe(response -> {
            System.out.println(response);
            atomicBoolean.set(true);
        });
        await().untilTrue(atomicBoolean);

    }

}