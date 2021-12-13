package com.github.marceloleite2604.cryptotrader.service;

import com.github.marceloleite2604.cryptotrader.model.Instrument;
import com.github.marceloleite2604.cryptotrader.model.Ticker;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.model.orderbook.OrderBook;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class MercadoBitcoinService {

  private final InstrumentService instrumentService;

  private final OrderBookService orderBookService;

  private final TickersService tickersService;

  private final CandlesService candlesService;

  @Cacheable("default")
  public Set<Instrument> retrieveAllInstruments() {
    return instrumentService.retrieveAllInstruments();
  }

  public OrderBook retrieveOrderBook(String symbol) {
    return retrieveOrderBook(symbol, null);
  }

  public OrderBook retrieveOrderBook(String symbol, Integer limit) {
    return orderBookService.retrieveOrderBook(symbol, limit);
  }

  public Map<String, Ticker> retrieveTickers(String... symbols) {
    return tickersService.retrieveTickers(symbols);
  }

  public List<Candle> retrieveCandles(CandlesRequest candlesRequest) {
    return candlesService.retrieveCandles(candlesRequest);
  }
}
