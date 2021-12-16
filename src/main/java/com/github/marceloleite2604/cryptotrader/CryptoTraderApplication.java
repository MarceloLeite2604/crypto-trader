package com.github.marceloleite2604.cryptotrader;

import com.github.marceloleite2604.cryptotrader.model.orders.PlaceOrderRequest;
import com.github.marceloleite2604.cryptotrader.model.orders.RetrieveOrdersRequest;
import com.github.marceloleite2604.cryptotrader.service.MercadoBitcoinService;
import com.github.marceloleite2604.cryptotrader.util.CurrencyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.math.BigDecimal;

@EnableCaching
@SpringBootApplication
@EnableScheduling
@Slf4j
public class CryptoTraderApplication {

  public static void main(String[] args) {
    SpringApplication.run(CryptoTraderApplication.class, args);
  }

  public CommandLineRunner createCommandLineRunner(MercadoBitcoinService mercadoBitcoinService, CurrencyUtil currencyUtil) {
    return (args -> {
//      final var instruments = mercadoBitcoinService.retrieveAllInstruments();
//      final var bitcoinInstrument = instruments.stream()
//        .filter(instrument -> instrument.getSymbol()
//          .equals("BTC-BRL"))
//        .findFirst()
//        .orElseThrow(() -> new IllegalStateException("Could not find Bitcoin cryptocurrency."));
//
//      final var orderBook = mercadoBitcoinService.retrieveOrderBook(bitcoinInstrument.getSymbol());
//      log.info("Order book asks: {}", orderBook.getAsks()
//        .size());
//      log.info("Order book bids: {}", orderBook.getBids()
//        .size());
//      log.info("Timestamp: {}", orderBook.getTimestamp());
//
//      final var tickers = mercadoBitcoinService.retrieveTickers("BTC-BRL");
//      final var btcBrlTicker = tickers.get("BTC-BRL");
//      log.info("Last price for \"{}\" instrument was {}", btcBrlTicker.getPair(), currencyUtil.toBrl(btcBrlTicker.getLast()));
//      final var now = OffsetDateTime.now(ZoneOffset.UTC);
//      final var candlesRequestTime = CandlesRequest.builder()
//        .symbol("BTC-BRL")
//        .resolution(CandlePrecision.FIFTEEN_MINUTES)
//        .toTime(now)
//        .from(now.minus(1, ChronoUnit.HOURS))
//        .build();
//      final var candlesRequestCount = CandlesRequest.builder()
//        .symbol("BTC-BRL")
//        .resolution(CandlePrecision.FIFTEEN_MINUTES)
//        .toCount(0)
//        .countback(4)
//        .build();
//      final var candles = mercadoBitcoinService.retrieveCandles(candlesRequestCount);

//      final var to = OffsetDateTime.now(ZoneOffset.UTC);
//      final var from = to.minus(1, ChronoUnit.HOURS);
//      final var tradesRequest = TradesRequest.builder()
//        .symbol("BTC-BRL")
//        .from(from)
//        .to(to)
//        .build();
//      final var trades = mercadoBitcoinService.retrieveTrades(tradesRequest);

      final var accounts = mercadoBitcoinService.retrieveAccounts();
      final var account = accounts.get(0);
//      final var balances = mercadoBitcoinService.retrieveBalances(account.getId(), "BTC-BRL");
//      final var positions = mercadoBitcoinService.retrievePositions(account.getId(), "BTC-BRL");
      final var retrieveOrdersRequest = RetrieveOrdersRequest.builder()
        .accountId(account.getId())
        .symbol("ETH-BRL")
        .build();
      final var orders = mercadoBitcoinService.retrieveOrders(retrieveOrdersRequest);
      final var order = orders.get(0);
//      final var retrievedOrder = mercadoBitcoinService.retrieveOrder(account.getId(), "BTC-BRL", order.getId());
//      final var placeOrderRequest = PlaceOrderRequest.builder()
//        .async(true)
//        .accountId(account.getId())
//        .symbol("ETH-BRL")
//        .limitPrice(BigDecimal.valueOf(21640.2236))
//        .quantity(BigDecimal.valueOf(0.0001))
//        .side("sell")
//        .type("limit")
//        .build();

      final var placeOrderRequest = PlaceOrderRequest.builder()
        .async(true)
        .accountId(account.getId())
        .symbol("ETH-BRL")
        .cost(BigDecimal.valueOf(9.99))
        .side("buy")
        .type("market")
        .build();
      log.info("Placing order.");
      final var optionalOrderId = mercadoBitcoinService.placeOrder(placeOrderRequest);

      if (optionalOrderId.isPresent()) {
        log.info("Order placed.");
        final var cancelled = mercadoBitcoinService.cancelOrder(account.getId(), "ETH-BRL", optionalOrderId.get());
        if (!cancelled) {
          log.error("Something went wrong while canceling order {}.", optionalOrderId.get());
        }
      } else {
        log.info("Could not place order.");
      }

      log.info("Done");
    });
  }
}

