package com.github.marceloleite2604.cryptotrader;

import com.github.marceloleite2604.cryptotrader.model.Side;
import com.github.marceloleite2604.cryptotrader.model.Symbol;
import com.github.marceloleite2604.cryptotrader.model.account.Account;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.model.orders.Order;
import com.github.marceloleite2604.cryptotrader.model.orders.RetrieveOrdersRequest;
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

import java.math.BigDecimal;
import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskAnalyser {

  private static final Symbol SYMBOL = Symbol.ETHEREUM;

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

  //  @Scheduled(cron = "0 * * ? * ?")
  @Scheduled(fixedDelay = 60000)
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
      .symbol(SYMBOL.getValue())
      .resolution(CANDLE_PRECISION)
      .toTime(to)
      .from(from)
      .build();
  }

  private void checkProfit() {

    final var balance = mercadoBitcoinService.retrieveBalance(retrieveAccount().getId(), "ETH");

    if (balance.getTotal()
      .compareTo(BigDecimal.valueOf(0.000001)) < 0) {
      System.out.printf("Not enough %s balance to check profit. Skipping.%n", SYMBOL.getName());
      return;
    }

    final var ticker = mercadoBitcoinService.retrieveTicker(SYMBOL.getValue());
    final var orders = retrieveOrders();
    final var profit = profitService.calculate(orders, ticker);

    var thresholds = profitService.retrieveThresholds(account.getId());

    final var percentage = profit.getPercentage();

    if (percentage.compareTo(thresholds.getUpper()) >= 0 ||
      percentage.compareTo(thresholds.getLower()) <= 0) {
      thresholds = profitService.updateThresholds(percentage, account.getId());

      if (percentage.compareTo(thresholds.getLower()) <= 0) {
        mailService.send(profit, Side.SELL);
      }
    }
    System.out.printf("Current profit: %s <= %s <= %s%n",
      formatUtil.toPercentage(thresholds.getLower()),
      formatUtil.toPercentage(thresholds.getCurrent()),
      formatUtil.toPercentage(thresholds.getUpper()));
  }

  private List<Order> retrieveOrders() {
    final var account = retrieveAccount();
    final var retrieveOrdersRequest = RetrieveOrdersRequest.builder()
      .accountId(account.getId())
      .symbol(SYMBOL.getValue())
      .build();
    return mercadoBitcoinService.retrieveOrders(retrieveOrdersRequest);
  }

  private Account retrieveAccount() {
    if (account == null) {
      account = mercadoBitcoinService.retrieveAccounts()
        .get(0);
    }
    return account;
  }
}
