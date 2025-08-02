package guru.springframework.spring_6_cool_service.services;

import guru.springframework.spring6restmvcapi.events.DrinkRequestEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DrinkRequestProcessorImpl implements DrinkRequestProcessor {
    @Override
    public void processDrinkRequest(DrinkRequestEvent event) {
        log.debug("Processing drink request...");

        try {
            Thread.sleep(50);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
