package course.spring.spring_6_rest_mvc.services;

import course.spring.spring_6_rest_mvc.entities.Beer;
import course.spring.spring_6_rest_mvc.events.BeerCreatedEvent;
import course.spring.spring_6_rest_mvc.mappers.BeerMapper;
import course.spring.spring_6_rest_mvc.model.BeerDTO;
import course.spring.spring_6_rest_mvc.model.BeerStyle;
import course.spring.spring_6_rest_mvc.repositories.BeerRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.security.core.Authentication;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

@Slf4j
@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;
    private final CacheManager cacheManager;
    private final ApplicationEventPublisher applicationEventPublisher;

    private static final int DEFAULT_PAGE=0;
    private static final int DEFAULT_PAGE_SIZE = 25;

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize) {
        int queryPageNumber;
        int queryPageSize;

        if (pageNumber != null && pageNumber > 0) {
            queryPageNumber = pageNumber - 1;
        } else {
            queryPageNumber = DEFAULT_PAGE;
        }

        if (pageSize == null) {
            queryPageSize = DEFAULT_PAGE_SIZE;
        } else {
            if (pageSize > 1000) {
                queryPageSize = 1000;
            } else {
                queryPageSize = pageSize;
            }
        }

        Sort sort = Sort.by(Sort.Order.asc("beerName"));
        return PageRequest.of(queryPageNumber, queryPageSize,sort);
    }

    Page<Beer> listBeersByName(String beerName, Pageable pageable ){
        return beerRepository.findByBeerNameContainingIgnoreCase(beerName, pageable);
    }

    Page<Beer> listBeersByStyle(BeerStyle beerStyle,Pageable pageable){
        return beerRepository.findAllByBeerStyle(beerStyle, pageable);
    }

    Page<Beer> listBeersByNameAndStyle(String beerName, BeerStyle beerStyle,Pageable pageable){
        return beerRepository.findByBeerNameContainingIgnoreCaseAndBeerStyle(beerName,beerStyle, pageable);
    }
    @Cacheable(cacheNames = "beerListCache")
    @Override
    public Page<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {

        log.info("list beer - in service");

        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize);

        Page<Beer> beerPage;

        if(StringUtils.hasText(beerName) && beerStyle== null){
            beerPage = listBeersByName(beerName,pageRequest);

        } else if (!StringUtils.hasText(beerName) && beerStyle != null) {
            beerPage = listBeersByStyle(beerStyle,pageRequest);
        }
        else if (StringUtils.hasText(beerName) && beerStyle != null){
            beerPage = listBeersByNameAndStyle(beerName,beerStyle,pageRequest);
        } else {
            beerPage = beerRepository.findAll(pageRequest);
        }

        if(showInventory !=null && !showInventory){
            beerPage.forEach(beer -> beer.setQuantityOnHand(null));
        }

        return beerPage.map(beerMapper::beerToBeerDto);

//        return beerList
//                .stream()
//                .map(beerMapper::beerToBeerDto)
//                .collect(Collectors.toList());
    }

    @Cacheable(cacheNames = "beerCache", key = "#id")
    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {

        log.info("get your beer by Id - in service");

        return Optional.ofNullable(beerMapper.beerToBeerDto(beerRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public BeerDTO saveNewBeer(BeerDTO beerDTO) {
       if(cacheManager.getCache("beerListCache") != null){
           cacheManager.getCache("beerListCache").clear();
       }

       val savedBeer = beerRepository.save(beerMapper.beerDtoToBeer(beerDTO));

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        applicationEventPublisher.publishEvent(new BeerCreatedEvent(savedBeer,auth));

        return beerMapper.beerToBeerDto(savedBeer);



//       Beer beer = beerMapper.beerDtoToBeer(beerDTO);
//       Beer savedBeer = beerRepository.save(beer);
//       return beerMapper.beerToBeerDto(savedBeer);
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID id, BeerDTO beer) {
        clearCache(id);

        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(id).ifPresentOrElse(foundBeer ->{
           foundBeer.setBeerName(beer.getBeerName());
           foundBeer.setBeerStyle(beer.getBeerStyle());
           foundBeer.setUpc(beer.getUpc());
           foundBeer.setPrice(beer.getPrice());
           atomicReference.set(Optional.of(beerMapper.beerToBeerDto(beerRepository.save(foundBeer))));
        },() -> {
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();

    }

//    @Caching(evict = {
//            @CacheEvict(cacheNames = "beerCache",key = "#id"),
//            @CacheEvict(cacheNames = "beerListCache")
//    })
    @Override
    public Boolean deleteBeerById(UUID beerId) {
        clearCache(beerId);

        if(beerRepository.existsById(beerId)){
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;

    }

    private void clearCache(UUID beerId){
        cacheManager.getCache("beerCache").evict(beerId);
        cacheManager.getCache("beerListCache").clear();
    }

    @Override
    public Optional<BeerDTO> patchBeerById(UUID beerId, BeerDTO beer) {
        clearCache(beerId);

        AtomicReference<Optional<BeerDTO>> atomicReference = new AtomicReference<>();

        beerRepository.findById(beerId).ifPresentOrElse(foundBeer ->{
            if(StringUtils.hasText(beer.getBeerName())){
                foundBeer.setBeerName(beer.getBeerName());
            }
            if(beer.getBeerStyle() != null){
                foundBeer.setBeerStyle(beer.getBeerStyle());
            }
            if(StringUtils.hasText(beer.getUpc())){
                foundBeer.setUpc(beer.getUpc());
            }
            if(beer.getPrice() != null){
                foundBeer.setPrice(beer.getPrice());
            }
            if(beer.getQuantityOnHand() != null){
                foundBeer.setQuantityOnHand(beer.getQuantityOnHand());
            }
            atomicReference.set(Optional.of(beerMapper.beerToBeerDto(
                    beerRepository.save(foundBeer)
            )));

        },() ->{
            atomicReference.set(Optional.empty());
        });

        return atomicReference.get();
    }
}
