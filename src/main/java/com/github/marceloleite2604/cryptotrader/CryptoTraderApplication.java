package com.github.marceloleite2604.cryptotrader;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import com.github.marceloleite2604.cryptotrader.service.candle.CandleComparisonService;
import com.github.marceloleite2604.cryptotrader.service.candle.CandleService;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternService;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@EnableCaching
@SpringBootApplication
@EnableScheduling
@Slf4j
public class CryptoTraderApplication {

  public static void main(String[] args) {
    SpringApplication.run(CryptoTraderApplication.class, args);
  }

  @Bean
  public CommandLineRunner createCommandLineRunner(
    CandleService candleService,
    DateTimeUtil dateTimeUtil,
    PatternService patternService,
    CandleComparisonService candleComparisonService) {
    return (args -> {
      final var listSize = 12;
      final var active = Active.BITCOIN;
      final var precision = CandlePrecision.ONE_DAY;
      final var duration = precision.getDuration();
      final var to = dateTimeUtil.truncateTo(
        OffsetDateTime.now(ZoneOffset.UTC),
        duration);
      final var from = to.minus(duration.multipliedBy(100));

      final var candlesRequest = CandlesRequest.builder()
        .resolution(precision)
        .from(from)
        .toTime(to)
        .active(active)
        .build();

      final var candles = candleService.retrieveCandles(candlesRequest);

      List<PatternMatch> patternMatches = new ArrayList<>();

      for (int count = candles.size(); count >= listSize; count--) {
        var selectedCandles = candles.subList(count - listSize, count);
        selectedCandles = candleComparisonService.compare(selectedCandles);
        selectedCandles.sort(Comparator.reverseOrder());
        final var patternMatchesForSelectedCandles = patternService.check(active, selectedCandles);
        patternMatches.addAll(patternMatchesForSelectedCandles);
      }

      if (CollectionUtils.isNotEmpty(patternMatches)) {
        patternMatches.sort(Comparator.comparing(PatternMatch::getCandleTime));
        log.info("The following patterns were found:");
        patternMatches.forEach(patternMatch -> log.info("{}: {}", patternMatch.getCandleTime(), patternMatch.getType()));

        patternMatches.stream()
          .collect(Collectors.groupingBy(PatternMatch::getType, Collectors.counting()))
          .forEach((type, count) -> log.info("{}: {}", type, count));
      }
    });
  }
}

