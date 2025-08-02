package guru.springframework.spring_6_icecold_service.services;

import guru.springframework.spring6restmvcapi.events.DrinkRequestEvent;

public interface DrinkRequestProcessor {

    void processDrinkRequest(DrinkRequestEvent event);
}
