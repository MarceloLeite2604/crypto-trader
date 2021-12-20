package com.github.marceloleite2604.cryptotrader;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.Side;
import com.github.marceloleite2604.cryptotrader.model.account.Account;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import com.github.marceloleite2604.cryptotrader.service.CandleService;
import com.github.marceloleite2604.cryptotrader.service.ProfitService;
import com.github.marceloleite2604.cryptotrader.service.mail.MailService;
import com.github.marceloleite2604.cryptotrader.service.mercadobitcoin.MercadoBitcoinService;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternService;
import com.github.marceloleite2604.cryptotrader.util.FormatUtil;
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
public class ScheduledTaskAnalyser {

  private static final Active ACTIVE = Active.ETHEREUM;

  private static final CandlePrecision CANDLE_PRECISION = CandlePrecision.FIFTEEN_MINUTES;

  private static final Duration TIME_WINDOW = CANDLE_PRECISION.getDuration()
    .multipliedBy(12);

  private final MercadoBitcoinService mercadoBitcoinService;

  private final PatternService patternService;

  private final CandleService candleService;

  private final MailService mailService;

  private final ProfitService profitService;

  private final FormatUtil formatUtil;

  private Account account;
  private List<PatternMatch> patternMatches;

  @Scheduled(cron = "0 * * ? * ?")
  public void run() {
    log.info("Looking for candle patterns.");
    lookForCandlePatterns();
    checkProfit();
    log.info("Done.");
  }

  private void lookForCandlePatterns() {
    final var candles = retrieveAndCompareCandles();
    final var patternMatches = patternService.check(candles);
    checkAndReportPatterns(patternMatches);
  }

  private List<Candle> retrieveAndCompareCandles() {
    var candles = retrieveCandles();
    return candleService.compare(candles);
  }

  private List<Candle> retrieveCandles() {
    final var candlesRequest = createCandlesRequest();
    return mercadoBitcoinService.retrieveCandles(candlesRequest);
  }

  private void checkAndReportPatterns(List<PatternMatch> patternMatches) {
    if (!patternMatches.equals(this.patternMatches)) {
      this.patternMatches = patternMatches;
      patternMatches.forEach(mailService::send);
    }
  }

  private CandlesRequest createCandlesRequest() {
    final var to = OffsetDateTime.now(ZoneOffset.UTC)
      .minus(CANDLE_PRECISION.getDuration());
    final var from = to.minus(TIME_WINDOW);
    return CandlesRequest.builder()
      .active(ACTIVE)
      .resolution(CANDLE_PRECISION)
      .toTime(to)
      .from(from)
      .build();
  }

  private void checkProfit() {

    final var activeBalance = mercadoBitcoinService.retrieveBalance(retrieveAccount().getId(), ACTIVE.getBase());
    final var fiatBalance = mercadoBitcoinService.retrieveBalance(retrieveAccount().getId(), "BRL");

    var profit = profitService.retrieve(retrieveAccount().getId(), ACTIVE);

    if (profit.hasReachedLowerLimit() && !activeBalance.isEmpty()) {
      mailService.send(profit, Side.SELL);
    }

    if (profit.hasReachedUpperLimit() && !fiatBalance.isEmpty()) {
      mailService.send(profit, Side.BUY);
    }

    profit = profitService.updateAndSave(profit);

    System.out.printf("Current profit: %s <= %s <= %s%n",
      formatUtil.toPercentage(profit.getLower()),
      formatUtil.toPercentage(profit.getCurrent()),
      formatUtil.toPercentage(profit.getUpper()));
  }


  private Account retrieveAccount() {
    if (account == null) {
      account = mercadoBitcoinService.retrieveAccount();
    }
    return account;
  }
}
