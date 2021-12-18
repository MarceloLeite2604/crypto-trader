package com.github.marceloleite2604.cryptotrader.service;

import com.github.marceloleite2604.cryptotrader.model.Instrument;
import com.github.marceloleite2604.cryptotrader.model.Ticker;
import com.github.marceloleite2604.cryptotrader.model.account.Account;
import com.github.marceloleite2604.cryptotrader.model.account.Balance;
import com.github.marceloleite2604.cryptotrader.model.account.Position;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.model.orderbook.OrderBook;
import com.github.marceloleite2604.cryptotrader.model.orders.PlaceOrderRequest;
import com.github.marceloleite2604.cryptotrader.model.orders.Order;
import com.github.marceloleite2604.cryptotrader.model.orders.RetrieveOrdersRequest;
import com.github.marceloleite2604.cryptotrader.model.trades.Trade;
import com.github.marceloleite2604.cryptotrader.model.trades.TradesRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Service
@Slf4j
public class MercadoBitcoinService {

  private final InstrumentService instrumentService;

  private final OrderBookService orderBookService;

  private final TickersService tickersService;

  private final CandlesService candlesService;

  private final TradesService tradesService;

  private final AccountsService accountsService;

  private final BalanceService balanceService;

  private final PositionService positionService;

  private final OrderService orderService;

  @Cacheable("default")
  public Set<Instrument> retrieveAllInstruments() {
    return instrumentService.retrieveAll();
  }

  public OrderBook retrieveOrderBook(String symbol) {
    return retrieveOrderBook(symbol, null);
  }

  public OrderBook retrieveOrderBook(String symbol, Integer limit) {
    return orderBookService.retrieve(symbol, limit);
  }

  public Ticker retrieveTicker(String symbol) {
    return tickersService.retrieve(symbol).get(symbol);
  }

  public Map<String, Ticker> retrieveTickers(String... symbols) {
    return tickersService.retrieve(symbols);
  }

  public List<Candle> retrieveCandles(CandlesRequest candlesRequest) {
    return candlesService.retrieve(candlesRequest);
  }

  public List<Trade> retrieveTrades(TradesRequest tradesRequest) {
    return tradesService.retrieve(tradesRequest);
  }

  @Cacheable("default")
  public List<Account> retrieveAccounts() {
    return accountsService.retrieve();
  }

  public List<Balance> retrieveBalances(String accountId, String symbol) {
    return balanceService.retrieve(accountId, symbol);
  }

  public List<Position> retrievePositions(String accountId, String symbol) {
    return positionService.retrieve(accountId, symbol);
  }

  public List<Order> retrieveOrders(RetrieveOrdersRequest retrieveOrdersRequest) {
    return orderService.retrieve(retrieveOrdersRequest);
  }

  public Order retrieveOrder(String accountId, String symbol, String orderId) {
    return orderService.retrieveOrder(accountId, symbol, orderId);
  }

  public Optional<String> placeOrder(PlaceOrderRequest placeOrderRequest) {
    return orderService.placeOrder(placeOrderRequest);
  }

  public boolean cancelOrder(String accountId, String symbol, String orderId) {
    return orderService.cancelOrder(accountId, symbol, orderId);
  }
}
