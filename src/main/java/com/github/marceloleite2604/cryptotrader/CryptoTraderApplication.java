package com.github.marceloleite2604.cryptotrader;

import com.github.marceloleite2604.cryptotrader.model.trades.Trade;
import com.github.marceloleite2604.cryptotrader.model.trades.TradesRequest;
import com.github.marceloleite2604.cryptotrader.service.MercadoBitcoinService;
import com.github.marceloleite2604.cryptotrader.util.CurrencyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.List;

@EnableCaching
@SpringBootApplication
@Slf4j
public class CryptoTraderApplication {

  public static void main(String[] args) {
    SpringApplication.run(CryptoTraderApplication.class, args);
  }

  @Bean
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

      final var to = OffsetDateTime.now(ZoneOffset.UTC);
      final var from = to.minus(1, ChronoUnit.HOURS);
      final var tradesRequest = TradesRequest.builder()
        .symbol("BTC-BRL")
        .from(from)
        .to(to)
        .build();
      final var trades = mercadoBitcoinService.retrieveTrades(tradesRequest);
      log.info("Done");
    });
  }
}

