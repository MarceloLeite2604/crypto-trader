package com.github.marceloleite2604.cryptotrader;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.service.MercadoBitcoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {

  private static final String ETHEREUM = "ETH-BRL";

  private final MercadoBitcoinService mercadoBitcoinService;

  private final CandlesRequest candlesRequest = CandlesRequest.builder()
    .symbol(ETHEREUM)
    .resolution(CandlePrecision.FIFTEEN_MINUTES)
    .toCount(0)
    .countback(1)
    .build();

  private List<Candle> candles = new ArrayList<>();

  //@Scheduled(cron = "0 0/15 * ? * ?")
  @Scheduled(cron = "0 * * ? * ?")
  public void check() {
    log.info("Checking candles.");
    final var retrievedCandles = mercadoBitcoinService.retrieveCandles(candlesRequest);

    candles.addAll(retrievedCandles);

    candles.forEach(System.out::println);
    log.info("Done.");
  }

}
