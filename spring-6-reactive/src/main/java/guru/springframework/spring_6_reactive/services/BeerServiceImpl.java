package guru.springframework.spring_6_reactive.services;

import guru.springframework.spring_6_reactive.domain.Beer;
import guru.springframework.spring_6_reactive.mappers.BeerMapper;
import guru.springframework.spring_6_reactive.model.BeerDTO;
import guru.springframework.spring_6_reactive.repositories.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Mono<Void> deleteBeerById(Integer beerId){
        return beerRepository.deleteById(beerId);

    }

    @Override
    public Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO){
        return beerRepository.save(beerMapper.toEntity(beerDTO))
                .map(beerMapper::toDTO);
    }

    @Override
    public Mono<BeerDTO> updateBeer(Integer id,BeerDTO beerDTO){
        return beerRepository.findById(id)
                .map(foundBeer ->{
                    foundBeer.setBeerName(beerDTO.getBeerName());
                    foundBeer.setBeerStyle(beerDTO.getBeerStyle());
                    foundBeer.setUpc(beerDTO.getUpc());
                    foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                    foundBeer.setPrice(beerDTO.getPrice());
                    return foundBeer;
                }).flatMap(beerRepository::save)
                .map(beerMapper::toDTO);
    }
    @Override
    public Mono<BeerDTO> patchBeer(Integer id, BeerDTO beerDTO){
        return beerRepository.findById(id)
                .map(foundBeer ->{
                    if(StringUtils.hasText(beerDTO.getBeerName())){
                        foundBeer.setBeerName(beerDTO.getBeerName());
                    }
                    if (StringUtils.hasText(beerDTO.getBeerStyle())) {
                        foundBeer.setBeerStyle(beerDTO.getBeerStyle());
                    }
                    if (StringUtils.hasText(beerDTO.getUpc())) {
                        foundBeer.setUpc(beerDTO.getUpc());
                    }
                    if(beerDTO.getQuantityOnHand() != null) foundBeer.setQuantityOnHand(beerDTO.getQuantityOnHand());
                    if(beerDTO.getPrice() !=  null) foundBeer.setPrice(beerDTO.getPrice());
                    return foundBeer;
                }).flatMap(beerRepository::save)
                .map(beerMapper::toDTO);
    }

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll()
                .map(beerMapper::toDTO);
    }

    @Override
    public Mono<BeerDTO> getBeerById(Integer id){
       return beerRepository.findById(id).map(beerMapper::toDTO);
    }
}
