package com.github.marceloleite2604.cryptotrader.service;

import com.github.marceloleite2604.cryptotrader.dto.orderbook.OrderBookResponsePayload;
import com.github.marceloleite2604.cryptotrader.dto.symbols.SymbolResponsePayload;
import com.github.marceloleite2604.cryptotrader.mapper.OrderBookResponsePayloadToOrderBookMapper;
import com.github.marceloleite2604.cryptotrader.mapper.SymbolsResponsePayloadToInstrumentSetMapper;
import com.github.marceloleite2604.cryptotrader.model.Instrument;
import com.github.marceloleite2604.cryptotrader.model.orderbook.OrderBook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URISyntaxException;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class MercadoBitcoinService {


  private final WebClient mbAuthenticatedWebClient;

  private final OrderBookResponsePayloadToOrderBookMapper orderBookResponsePayloadToOrderBookMapper;

  private final SymbolsResponsePayloadToInstrumentSetMapper symbolsResponsePayloadToInstrumentSetMapper;

  @Cacheable("default")
  public Set<Instrument> retrieveAllInstruments() {
    final var symbolResponsePayload = mbAuthenticatedWebClient.get()
      .uri("symbols")
      .retrieve()
      .bodyToMono(SymbolResponsePayload.class)
      .blockOptional()
      .orElseThrow(() -> new IllegalStateException("Could not retrieve all instruments."));

    return symbolsResponsePayloadToInstrumentSetMapper.mapTo(symbolResponsePayload);
  }

  public OrderBook retrieveOrderBook(String symbol, Integer limit) {
    final var orderBookUri = buildOrderBookUri(symbol, limit);

    final var orderBookResponsePayload = mbAuthenticatedWebClient.get()
      .uri(orderBookUri)
      .retrieve()
      .bodyToMono(OrderBookResponsePayload.class)
      .blockOptional()
      .orElseThrow(() -> {
        final var message = String.format("Could not retrieve order book for \"%s\" symbol.", symbol);
        return new IllegalStateException(message);
      });

    return orderBookResponsePayloadToOrderBookMapper.mapTo(orderBookResponsePayload);
  }

  public OrderBook retrieveOrderBook(String symbol) {
    return retrieveOrderBook(symbol, null);
  }

  private String buildOrderBookUri(String symbol, Integer limit) {
    final var orderBookUriBuilder = new URIBuilder().setPathSegments(symbol, "orderbook");

    if (limit != null) {
      orderBookUriBuilder.addParameter("limit", limit.toString());
    }

    try {
      return orderBookUriBuilder.build()
        .toString();
    } catch (URISyntaxException exception) {
      throw new IllegalStateException("Exception thrown while building order book URI.", exception);
    }
  }
}
