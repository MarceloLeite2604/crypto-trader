package com.github.marceloleite2604.cryptotrader;

import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleAnalysis;
import com.github.marceloleite2604.cryptotrader.model.patterns.PatternMatch;
import com.github.marceloleite2604.cryptotrader.service.CandleAnalyser;
import com.github.marceloleite2604.cryptotrader.service.MercadoBitcoinService;
import com.github.marceloleite2604.cryptotrader.service.mail.MailService;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternCheckService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTask {

  private static final String ETHEREUM = "ETH-BRL";

  private static final Duration TIME_WINDOW = Duration.of(2, ChronoUnit.DAYS);

  private final MercadoBitcoinService mercadoBitcoinService;

  private final PatternCheckService patternCheckService;

  private final CandleAnalyser candleAnalyser;

  private final MailService mailService;

  private List<CandleAnalysis> candleAnalyses = new ArrayList<>();

  //  @Scheduled(cron = "0 0/15 * ? * ?")
  @Scheduled(fixedDelay = 60000)
  public void check() {

    log.info("Checking candles.");
    candleAnalyses = retrieveAndAnalyseCandles(candleAnalyses);
    final List<PatternMatch> patternMatches = findPatterns(candleAnalyses);
    patternMatches.forEach(mailService::send);
    log.info("Done.");
  }

  private List<PatternMatch> findPatterns(List<CandleAnalysis> candleAnalyses) {

    List<PatternMatch> patternMatches = new ArrayList<>();
    for (int count = 12; count < candleAnalyses.size() - 12; count++) {
      final var ca = candleAnalyses.subList(count - 12, count);
      final var pm = patternCheckService.check(ca);
      patternMatches.addAll(pm);
    }
    return patternMatches;
  }

  private List<CandleAnalysis> retrieveAndAnalyseCandles(List<CandleAnalysis> candleAnalyses) {
    final var candlesRequest = createCandlesRequest();
    final var retrievedCandles = mercadoBitcoinService.retrieveCandles(candlesRequest);

    final var existingCandles = candleAnalyses.stream()
      .map(CandleAnalysis::getCandle)
      .toList();

    final var newCandles = retrievedCandles.stream()
      .filter(candle -> !existingCandles.contains(candle))
      .collect(Collectors.toCollection(ArrayList::new));

    if (CollectionUtils.isEmpty(newCandles)) {
      return candleAnalyses;
    }

    final var newCandlesAnalysis = newCandles.stream()
      .map(candle -> CandleAnalysis.builder()
        .candle(candle)
        .build())
      .toList();

    candleAnalyses.addAll(newCandlesAnalysis);
    candleAnalyses.sort(Collections.reverseOrder());

    return candleAnalyser.analyse(candleAnalyses);
  }

  private CandlesRequest createCandlesRequest() {
    final var now = OffsetDateTime.now(ZoneOffset.UTC);
    return CandlesRequest.builder()
      .symbol(ETHEREUM)
      .resolution(CandlePrecision.ONE_HOUR)
      .toTime(now)
      .from(now.minus(TIME_WINDOW))
      .build();
  }
}
