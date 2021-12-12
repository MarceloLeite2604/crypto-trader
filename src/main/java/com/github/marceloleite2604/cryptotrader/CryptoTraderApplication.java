package com.github.marceloleite2604.cryptotrader;

import com.github.marceloleite2604.cryptotrader.service.MercadoBitcoinService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@EnableCaching
@SpringBootApplication
@Slf4j
public class CryptoTraderApplication {

  public static void main(String[] args) {
    SpringApplication.run(CryptoTraderApplication.class, args);
  }

  @Bean
  public CommandLineRunner createCommandLineRunner(MercadoBitcoinService mercadoBitcoinService) {
    return (args -> {
      final var instruments = mercadoBitcoinService.retrieveAllInstruments();
      final var bitcoinInstrument = instruments.stream()
        .filter(instrument -> instrument.getSymbol()
          .equals("BTC-BRL"))
        .findFirst()
        .orElseThrow(() -> new IllegalStateException("Could not find Bitcoin cryptocurrency."));

      final var orderBook = mercadoBitcoinService.retrieveOrderBook(bitcoinInstrument.getSymbol());
      log.info("Order book asks: {}", orderBook.getAsks()
        .size());
      log.info("Order book bids: {}", orderBook.getBids()
        .size());
      log.info("Timestamp: {}", orderBook.getTimestamp());
    });
  }
}

