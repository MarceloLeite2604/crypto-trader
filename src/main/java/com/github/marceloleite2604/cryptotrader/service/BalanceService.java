package com.github.marceloleite2604.cryptotrader.service;

import com.github.marceloleite2604.cryptotrader.dto.account.BalanceDto;
import com.github.marceloleite2604.cryptotrader.mapper.BalanceDtoMapper;
import com.github.marceloleite2604.cryptotrader.model.account.Balance;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URISyntaxException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BalanceService {

  private final WebClient mbAuthenticatedWebClient;

  private final BalanceDtoMapper balanceDtoMapper;

  public List<Balance> retrieve(String accountId, String symbol) {
    final var getBalanceUri = buildGetBalancesUri(accountId, symbol);

    final var balanceDtos = mbAuthenticatedWebClient.get()
      .uri(getBalanceUri)
      .retrieve()
      .bodyToMono(new ParameterizedTypeReference<List<BalanceDto>>() {
      })
      .blockOptional()
      .orElseThrow(() -> new IllegalStateException("Could not retrieve all account balances."));

    return balanceDtoMapper.mapAllTo(balanceDtos);
  }

  private String buildGetBalancesUri(String accountId, String symbol) {
    Assert.isTrue(StringUtils.isNotBlank(accountId), "Account ID cannot be blank.");
    Assert.isTrue(StringUtils.isNotBlank(symbol), "Symbol cannot be blank.");

    final var getBalanceUriBuilder = new URIBuilder().setPathSegments("accounts", accountId, "balances")
      .addParameter("symbol", symbol);

    try {
      return getBalanceUriBuilder.build()
        .toString();
    } catch (URISyntaxException exception) {
      throw new IllegalStateException("Exception thrown while building account balances URI.", exception);
    }
  }
}
