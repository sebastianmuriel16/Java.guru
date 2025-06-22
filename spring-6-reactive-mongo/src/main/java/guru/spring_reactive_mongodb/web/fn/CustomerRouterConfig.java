package guru.spring_reactive_mongodb.web.fn;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
@RequiredArgsConstructor
public class CustomerRouterConfig {

    public static final String CUSTOMER_PATH = "/api/v3/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerHandler handler;

    @Bean
    public RouterFunction<ServerResponse> CustomerRoutes(){
        return route()
                .GET(CUSTOMER_PATH,accept(MediaType.APPLICATION_JSON),handler::listCustomers)
                .GET(CUSTOMER_PATH_ID,accept(MediaType.APPLICATION_JSON),handler::getCustomerById)
                .POST(CUSTOMER_PATH,accept(MediaType.APPLICATION_JSON),handler::createNewCustomer)
                .PUT(CUSTOMER_PATH_ID,accept(MediaType.APPLICATION_JSON),handler::updateCustomerById)
                .PATCH(CUSTOMER_PATH_ID,accept(MediaType.APPLICATION_JSON),handler::patchCustomerById)
                .DELETE(CUSTOMER_PATH_ID,accept(MediaType.APPLICATION_JSON),handler::deleteBeer)
                .build();
    }


}
