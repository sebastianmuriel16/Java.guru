package course.spring.spring_6_rest_mvc.events;

import course.spring.spring_6_rest_mvc.entities.Beer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class BeerCreatedEvent {

    private Beer beer;

    private Authentication authentication;

}
