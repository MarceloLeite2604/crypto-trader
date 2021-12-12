package com.github.marceloleite2604.cryptotrader.configuration;

import com.github.marceloleite2604.cryptotrader.dto.authorize.Authorization;
import com.github.marceloleite2604.cryptotrader.dto.authorize.AuthorizeResponsePayload;
import com.github.marceloleite2604.cryptotrader.mapper.AuthorizeResponsePayloadToAuthorizationMapper;
import com.github.marceloleite2604.cryptotrader.properties.MercadoBitcoinProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
@Slf4j
public class TokenRequestExchangeFilter implements ExchangeFilterFunction {

  private final WebClient mbUnauthenticatedWebClient;
  private final AuthorizeResponsePayloadToAuthorizationMapper authorizeResponsePayloadToAuthorizationMapper;
  private final BodyInserters.FormInserter<String> credentialsFormInserter;
  private Authorization authorization;

  public TokenRequestExchangeFilter(
    WebClient mbUnauthenticatedWebClient,
    AuthorizeResponsePayloadToAuthorizationMapper authorizeResponsePayloadToAuthorizationMapper,
    MercadoBitcoinProperties mercadoBitcoinProperties
  ) {
    this.mbUnauthenticatedWebClient = mbUnauthenticatedWebClient;
    this.authorizeResponsePayloadToAuthorizationMapper = authorizeResponsePayloadToAuthorizationMapper;
    this.credentialsFormInserter = createCredentialsFormInserter(mercadoBitcoinProperties);
  }

  private BodyInserters.FormInserter<String> createCredentialsFormInserter(
    MercadoBitcoinProperties mercadoBitcoinProperties) {
    return BodyInserters.fromFormData("login", mercadoBitcoinProperties.getClientId())
      .with("password", mercadoBitcoinProperties.getClientSecret());
  }

  @Override
  public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {
    if (isNotValid(authorization)) {
      authorization = retrieveAuthorization();
    }

    final var clientRequest = ClientRequest.from(request)
      .header("Authorization", authorization.getToken())
      .build();
    return next.exchange(clientRequest);
  }

  private Authorization retrieveAuthorization() {
    return mbUnauthenticatedWebClient.post()
      .contentType(MediaType.MULTIPART_FORM_DATA)
      .body(credentialsFormInserter)
      .retrieve()
      .bodyToMono(AuthorizeResponsePayload.class)
      .blockOptional()
      .map(authorizeResponsePayloadToAuthorizationMapper::mapTo)
      .orElseThrow(() -> new IllegalStateException("Error while authenticating with Mercado Bitcoin."));
  }

  private boolean isNotValid(Authorization authorization) {
    return authorization == null ||
      OffsetDateTime.now(ZoneOffset.UTC)
        .compareTo(authorization.getExpiration()) > 0;
  }
}
