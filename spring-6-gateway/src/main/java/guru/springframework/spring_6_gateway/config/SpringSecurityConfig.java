package guru.springframework.spring_6_gateway.config;

import org.springframework.boot.actuate.autoconfigure.security.reactive.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
    
@Configuration
@EnableWebFluxSecurity
public class SpringSecurityConfig {

    @Bean
    @Order(1)
    public SecurityWebFilterChain actuatorSecurityFilterChain(ServerHttpSecurity http){
        http.securityMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeExchange(authorize ->authorize.anyExchange().permitAll());

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http.authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .pathMatchers("/oauth2/**","/oauth2/token").permitAll()
                        .anyExchange().authenticated())
                .oauth2ResourceServer(oAuth2ResourceServerSpec ->
                        oAuth2ResourceServerSpec.jwt(Customizer.withDefaults()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable);

        return http.build();
    }

}
