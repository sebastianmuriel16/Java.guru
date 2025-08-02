package guru.springframework.spring_6_cold_service.services;

import guru.springframework.spring6restmvcapi.events.DrinkRequestEvent;

public interface DrinkRequestProcessor {

    void processDrinkRequest(DrinkRequestEvent event);
}
