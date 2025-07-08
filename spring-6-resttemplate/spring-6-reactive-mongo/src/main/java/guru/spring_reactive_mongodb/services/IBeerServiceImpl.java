package guru.spring_reactive_mongodb.services;

import guru.spring_reactive_mongodb.domain.Beer;
import guru.spring_reactive_mongodb.mappers.BeerMapper;
import guru.spring_reactive_mongodb.model.BeerDTO;
import guru.spring_reactive_mongodb.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class IBeerServiceImpl implements IBeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper;

    @Override
    public Flux<BeerDTO> findByBeerStyle(String beerStyle) {
        return beerRepository.findByBeerStyle(beerStyle)
                .map(beerMapper::toDTO);
    }

    @Override
    public Mono<BeerDTO> findFirstByBeerName(String beerName) {
        return beerRepository.findFirstByBeerName(beerName)
                .map(beerMapper::toDTO);
    }

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .map(beerMapper::toDTO);
    }

    @Override
    public Mono<BeerDTO> saveBeer(BeerDTO beerDTO) {
        Beer beer = beerMapper.toEntity(beerDTO);
        return beerRepository.save(beer)
                .map(beerMapper::toDTO);
    }

    @Override
    public Mono<BeerDTO> saveBeer(Mono<BeerDTO> beerDTO) {
        return beerDTO.map(beerMapper::toEntity)
                .flatMap(beerRepository::save)
                .map(beerMapper::toDTO);
    }

    @Override
    public Mono<BeerDTO> getById(String beerId) {
        return beerRepository.findById(beerId)
                .map(beerMapper::toDTO);
    }

    @Override
    public Mono<BeerDTO> updateBeer(String beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId)
                .map(foundBeer -> {
                     foundBeer.setBeerName(beerDTO.getBeerName());
                     foundBeer.setBeerStyle(beerDTO.getBeerStyle());
                     foundBeer.setUpc(beerDTO.getUpc());
                     foundBeer.setPrice(beerDTO.getPrice());
                     return foundBeer;
                })
                .flatMap(beerRepository::save)
                .map(beerMapper::toDTO);
    }

    @Override
    public Mono<BeerDTO> patchBeer(String beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId)
                .map(foundBeer ->{
                    if (StringUtils.hasText(beerDTO.getBeerName())){
                        foundBeer.setBeerName(beerDTO.getBeerName());
                    }

                    if (StringUtils.hasText(beerDTO.getBeerStyle())) {
                        foundBeer.setBeerStyle(beerDTO.getBeerStyle());
                    }

                    if (StringUtils.hasText(beerDTO.getUpc())) {
                        foundBeer.setUpc(beerDTO.getUpc());
                    }

                    if (beerDTO.getPrice() != null) {
                        foundBeer.setPrice(beerDTO.getPrice());
                    }

                    if (beerDTO.getQuantityOnHand() != null) {
                        foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                    }
                    return foundBeer;
                }).flatMap(beerRepository::save)
                .map(beerMapper::toDTO);
    }

    @Override
    public Mono<Void> deleteBeerById(String beerId) {
        return beerRepository.deleteById(beerId);
    }
}
