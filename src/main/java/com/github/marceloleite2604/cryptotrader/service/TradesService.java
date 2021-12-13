package com.github.marceloleite2604.cryptotrader.service;

import com.github.marceloleite2604.cryptotrader.dto.trades.TradeDto;
import com.github.marceloleite2604.cryptotrader.mapper.ListTradeDtoToListTradeMapper;
import com.github.marceloleite2604.cryptotrader.model.trades.Trade;
import com.github.marceloleite2604.cryptotrader.model.trades.TradesRequest;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
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
public class TradesService {

  private final WebClient mbAuthenticatedWebClient;

  private final DateTimeUtil dateTimeUtil;

  private final ListTradeDtoToListTradeMapper listTradeDtoToListTradeMapper;

  public List<Trade> retrieve(TradesRequest tradesRequest) {
    final var getTradesUri = buildRetrieveUri(tradesRequest);

    final var tradeDtos = mbAuthenticatedWebClient.get()
      .uri(getTradesUri)
      .retrieve()
      .bodyToMono(new ParameterizedTypeReference<List<TradeDto>>() {
      })
      .blockOptional()
      .orElseThrow(() -> {
        final var message = String.format("Could not retrieve trades for \"%s\" symbol.", tradesRequest.getSymbol());
        return new IllegalStateException(message);
      });

    return listTradeDtoToListTradeMapper.mapTo(tradeDtos);
  }

  private String buildRetrieveUri(TradesRequest tradesRequest) {
    Assert.notNull(tradesRequest, "Must inform a valid trades request.");
    Assert.isTrue(StringUtils.isNotBlank(tradesRequest.getSymbol()), "Must inform a symbol.");
    Assert.isTrue((
        (tradesRequest.getTo() == null && tradesRequest.getFrom() == null) ||
          (tradesRequest.getTo() != null && tradesRequest.getFrom() != null))
      , "Must both \"from\" and \"to\" parameters.");

    final var uriBuilder = new URIBuilder().setPathSegments(tradesRequest.getSymbol(), "trades");

    if (tradesRequest.getTid() != null) {
      uriBuilder.addParameter("tid", Long.toString(tradesRequest.getTid()));
    }

    if (tradesRequest.getSince() != null) {
      uriBuilder.addParameter("since", Long.toString(tradesRequest.getSince()));
    }

    if (tradesRequest.getFrom() != null) {
      uriBuilder.addParameter("from", Long.toString(tradesRequest.getFrom()
        .toEpochSecond()));
    }

    if (tradesRequest.getTo() != null) {
      uriBuilder.addParameter("to", Long.toString(tradesRequest.getTo()
        .toEpochSecond()));
    }

    try {
      return uriBuilder
        .build()
        .toString();
    } catch (URISyntaxException exception) {
      throw new IllegalStateException("Exception thrown while building trades URI.", exception);
    }
  }
}