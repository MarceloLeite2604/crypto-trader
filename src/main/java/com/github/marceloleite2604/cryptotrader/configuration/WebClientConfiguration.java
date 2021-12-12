package com.github.marceloleite2604.cryptotrader.configuration;

import com.github.marceloleite2604.cryptotrader.properties.MercadoBitcoinProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Configuration
public class WebClientConfiguration {

    @Bean(BeanNames.MB_UNAUTHENTICATED_WEB_CLIENT)
    public WebClient createMbUnauthenticatedWebClient(MercadoBitcoinProperties mercadoBitcoinProperties) {
        final var authorizeUri = URI.create(mercadoBitcoinProperties.getBaseUri())
          .resolve("authorize");

        return WebClient.builder()
          .baseUrl(authorizeUri.toString())
          .build();
    }

    @Bean(BeanNames.MB_AUTHENTICATED_WEB_CLIENT)
    public WebClient createMbAuthenticatedWebClient(
            MercadoBitcoinProperties mercadoBitcoinProperties,
            TokenRequestExchangeFilter tokenRequestExchangeFilter) {
        return WebClient.builder()
                .baseUrl(mercadoBitcoinProperties.getBaseUri())
                .filter(tokenRequestExchangeFilter)
                .build();
    }
}
