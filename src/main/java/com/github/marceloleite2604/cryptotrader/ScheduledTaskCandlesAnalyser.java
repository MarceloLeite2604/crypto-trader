package com.github.marceloleite2604.cryptotrader;

import com.github.marceloleite2604.cryptotrader.model.Symbol;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskCandlesAnalyser {

  private static final Symbol SYMBOL = Symbol.ETHEREUM;

  private static final CandlePrecision CANDLE_PRECISION = CandlePrecision.FIFTEEN_MINUTES;

  private static final Duration TIME_WINDOW = CANDLE_PRECISION.getDuration().multipliedBy(12);

  private final MercadoBitcoinService mercadoBitcoinService;

  private final PatternCheckService patternCheckService;

  private final CandleAnalyser candleAnalyser;

  private final MailService mailService;

  private List<PatternMatch> patternMatches;

  @Scheduled(cron = "0 * * ? * ?")
  public void run() {
    log.info("Retrieving and analysing candles.");
    final var candleAnalyses = retrieveAndAnalyseCandles();
    final var patternMatches = findPatterns(candleAnalyses);
    checkAndReportPatterns(patternMatches);
    log.info("Done.");
  }

  private void checkAndReportPatterns(List<PatternMatch> patternMatches) {
    if (!patternMatches.equals(this.patternMatches)) {
      this.patternMatches = patternMatches;
      patternMatches.forEach(mailService::send);
    }
  }

  private List<PatternMatch> findPatterns(List<CandleAnalysis> candleAnalyses) {
    return patternCheckService.check(candleAnalyses);
  }

  private List<CandleAnalysis> retrieveAndAnalyseCandles() {
    final var candlesRequest = createCandlesRequest();
    final var retrievedCandles = mercadoBitcoinService.retrieveCandles(candlesRequest);

    final var candleAnalyses = retrievedCandles.stream()
      .map(candle -> CandleAnalysis.builder()
        .candle(candle)
        .build())
      .toList();

    return candleAnalyser.analyse(candleAnalyses);
  }

  private CandlesRequest createCandlesRequest() {
    final var to = OffsetDateTime.now(ZoneOffset.UTC)
      .minus(CANDLE_PRECISION.getDuration());
    final var from = to.minus(TIME_WINDOW);
    return CandlesRequest.builder()
      .symbol(SYMBOL.getValue())
      .resolution(CANDLE_PRECISION)
      .toTime(to)
      .from(from)
      .build();
  }
}
