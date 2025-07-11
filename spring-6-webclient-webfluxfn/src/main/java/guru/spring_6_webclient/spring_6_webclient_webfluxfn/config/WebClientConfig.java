package guru.spring_6_webclient.spring_6_webclient_webfluxfn.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizationCodeAuthenticationTokenConverter;
import org.springframework.web.reactive.function.client.WebClient;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.core.DefaultHttpLogFormatter;
import org.zalando.logbook.core.DefaultHttpLogWriter;
import org.zalando.logbook.core.DefaultSink;
import org.zalando.logbook.spring.webflux.LogbookExchangeFilterFunction;

@Configuration
public class WebClientConfig implements WebClientCustomizer {

    private final String rootUrl;
    private final ReactiveOAuth2AuthorizedClientManager auth2AuthorizedClientManager;

    public WebClientConfig(@Value("${webclient.rooturl}") String rootUrl,
                           ReactiveOAuth2AuthorizedClientManager auth2AuthorizedClientManager){
        this.rootUrl = rootUrl;
        this.auth2AuthorizedClientManager = auth2AuthorizedClientManager;
    }

    @Override
    public void customize(WebClient.Builder webClientBuilder) {
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth
                = new ServerOAuth2AuthorizedClientExchangeFilterFunction(auth2AuthorizedClientManager);
        oauth.setDefaultClientRegistrationId("springauth");



        LogbookExchangeFilterFunction logbookWebFilter = new LogbookExchangeFilterFunction(Logbook.builder().build());

        webClientBuilder.filter(oauth).filter(logbookWebFilter).baseUrl(rootUrl);
    }
}
