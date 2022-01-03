package com.github.marceloleite2604.cryptotrader.service;

import com.github.marceloleite2604.cryptotrader.model.Action;
import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.AnalysisContext;
import com.github.marceloleite2604.cryptotrader.model.OffsetDateTimeRange;
import com.github.marceloleite2604.cryptotrader.model.Side;
import com.github.marceloleite2604.cryptotrader.model.account.Account;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.service.candle.CandleService;
import com.github.marceloleite2604.cryptotrader.service.mail.MailService;
import com.github.marceloleite2604.cryptotrader.service.mercadobitcoin.MercadoBitcoinService;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternService;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import com.github.marceloleite2604.cryptotrader.util.FormatUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalyserService {

  private final MercadoBitcoinService mercadoBitcoinService;

  private final PatternService patternService;

  private final CandleService candleService;

  private final MailService mailService;

  private final ProfitService profitService;

  private final FormatUtil formatUtil;

  private final DateTimeUtil dateTimeUtil;

  private Account account;

  public void analyse(Active active) {
    final var accountId = retrieveAccount().getId();

    final var shortRangeCandles = retrieveShortRangeCandles(active);
    final var longRangeCandles = retrieveLongRangeCandles(active);
    final var shortRangePatterns = patternService.check(active, shortRangeCandles);
    final var longRangePatterns = patternService.check(active, longRangeCandles);
    final var profit = profitService.retrieve(accountId, active);
    final var activeBalance = mercadoBitcoinService.retrieveBalance(accountId, active.getBase());
    final var fiatBalance = mercadoBitcoinService.retrieveBalance(accountId, active.getQuote());

    final var analysisContext = AnalysisContext.builder()
      .active(active)
      .activeBalance(activeBalance)
      .fiatBalance(fiatBalance)
      .longRangeCandles(longRangeCandles)
      .shortRangeCandles(shortRangeCandles)
      .shortRangePatterns(shortRangePatterns)
      .longRangePatterns(longRangePatterns)
      .profit(profit)
      .build();

    elaborateAction(analysisContext).ifPresent(mailService::send);

    if (activeBalance.isNotEmpty()) {
      log.debug("{} ({})", profitService.toStringPrice(profit), profitService.toStringPercentage(profit));
    } else {
      log.debug("{} analysed.", active.getName());
    }

    profitService.updateAndSave(profit);
  }

  private Optional<Action> elaborateAction(AnalysisContext analysisContext) {


    final var sellPatternFound = analysisContext.retrieveSellPatternMatches()
      .size() > 0;

    final var buyPatternFound = analysisContext.retrieveBuyPatternMatches()
      .size() > 0;

    final var hasFiatBalance = analysisContext.getFiatBalance()
      .isNotEmpty();

    final var hasActiveBalance = analysisContext.getActiveBalance()
      .isNotEmpty();

    final var profit = analysisContext.getProfit();

    final var upperLimitReached = profit.hasReachedUpperLimit();

    final var lowerLimitReached = profit.hasReachedLowerLimit();

    final var sellArguments = retrieveSellArguments(analysisContext);
    final var buyArguments = retrieveBuyArguments(analysisContext);

    final var decisionFactors = DecisionFactors.builder()
      .upperLimitReached(upperLimitReached)
      .lowerLimitReached(lowerLimitReached)
      .hasActiveBalance(hasActiveBalance)
      .hasFiatBalance(hasFiatBalance)
      .buyPatternFound(buyPatternFound)
      .sellPatternFound(sellPatternFound)
      .build();

    if (!hasActiveBalance && !hasFiatBalance) {
      return Optional.empty();
    }

    if (hasActiveBalance && !hasFiatBalance) {
      if (sellPatternFound) {
        if (buyPatternFound && upperLimitReached) {
          return Optional.empty();
        }
        log.debug("Selling - Decision factors: {}", decisionFactors);
        return Optional.of(elaborateAction(analysisContext, Side.SELL, sellArguments));
      } else {
        if (lowerLimitReached) {
          return Optional.empty();
        }
      }
    }

    if (!hasActiveBalance && buyPatternFound) {
      if (!sellPatternFound) {
        log.debug("Buying - Decision factors: {}", decisionFactors);
        return Optional.of(elaborateAction(analysisContext, Side.BUY, buyArguments));
      }
      return Optional.empty();
    }

    if (lowerLimitReached) {
      log.debug("Selling - Decision factors: {}", decisionFactors);
      return Optional.of(elaborateAction(analysisContext, Side.SELL, sellArguments));
    }

    if (!buyPatternFound && !sellPatternFound) {
      return Optional.empty();
    }

    if (buyPatternFound && !sellPatternFound) {
      log.debug("Buying - Decision factors: {}", decisionFactors);
      return Optional.of(elaborateAction(analysisContext, Side.BUY, buyArguments));
    }

    if (buyPatternFound && upperLimitReached) {
      log.debug("Buying - Decision factors: {}", decisionFactors);
      return Optional.of(elaborateAction(analysisContext, Side.BUY, buyArguments));
    }

    log.debug("Selling - Decision factors: {}", decisionFactors);
    return Optional.of(elaborateAction(analysisContext, Side.SELL, sellArguments));
  }

  private List<Candle> retrieveLongRangeCandles(Active active) {
    return retrieveCandles(CandlePrecision.FIFTEEN_MINUTES, active);
  }

  private List<Candle> retrieveShortRangeCandles(Active active) {
    return retrieveCandles(CandlePrecision.THREE_MINUTES, active);
  }

  private List<Candle> retrieveCandles(CandlePrecision resolution, Active active) {
    final var end = dateTimeUtil.truncateTo(
      OffsetDateTime.now(ZoneOffset.UTC),
      resolution.getDuration());

    var start = end
      .minus(resolution.getDuration()
        .multipliedBy(12));

    final var range = OffsetDateTimeRange.builder()
      .start(start)
      .end(end)
      .build();

    final var candlesRequest = createCandlesRequest(range, resolution, active);
    log.debug("Retrieving candles: {}", candlesRequest);
    return candleService.retrieveCandles(candlesRequest);
  }

  private CandlesRequest createCandlesRequest(OffsetDateTimeRange range, CandlePrecision resolution, Active active) {
    return CandlesRequest.builder()
      .active(active)
      .resolution(resolution)
      .toTime(range.getEnd())
      .from(range.getStart())
      .build();
  }

  private Account retrieveAccount() {
    if (account == null) {
      account = mercadoBitcoinService.retrieveAccount();
    }
    return account;
  }

  private Action elaborateAction(AnalysisContext analysisContext, Side side, List<String> arguments) {

    String summary;
    if (arguments.size() > 1) {
      summary = String.format("Strong advice to %s %s", side.name()
        .toLowerCase(), analysisContext.getActive()
        .getName());
    } else {
      summary = String.format("Advice to %s %s", side.name()
        .toLowerCase(), analysisContext.getActive()
        .getName());
    }

    return Action.builder()
      .active(analysisContext.getActive())
      .arguments(arguments)
      .summary(summary)
      .side(side)
      .build();
  }

  private List<String> retrieveBuyArguments(AnalysisContext analysisContext) {
    final var buyArguments = retrieveArguments(analysisContext, Side.BUY);

    final var profit = analysisContext.getProfit();

    if (profit.hasReachedUpperLimit()) {
      final var message = String.format(
        "Profit has reached the upper limit of %s. It is now %s.",
        formatUtil.toPercentage(profit.getUpper()),
        formatUtil.toPercentage(profit.getCurrent()));
      buyArguments.add(message);
    }

    return buyArguments;
  }

  private List<String> retrieveSellArguments(AnalysisContext analysisContext) {
    final var sellArguments = retrieveArguments(analysisContext, Side.SELL);

    final var profit = analysisContext.getProfit();

    if (profit.hasReachedLowerLimit()) {
      final var message = String.format(
        "Profit has reached the lower limit of %s. It is now %s.",
        formatUtil.toPercentage(profit.getLower()),
        formatUtil.toPercentage(profit.getCurrent()));
      sellArguments.add(message);
    }

    return sellArguments;
  }

  private List<String> retrieveArguments(AnalysisContext analysisContext, Side side) {
    return analysisContext.retrievePatternMatchesBySide(side)
      .stream()
      .map(patternMatch -> String.format("%s pattern found on %s range.", patternMatch.getType()
        .getName(), patternMatch.getCandlePrecision()
        .getDescription()))
      .collect(Collectors.toCollection(ArrayList::new));
  }

  /* TODO For debug purposes only. */
  @Builder
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @ToString
  private static class DecisionFactors {
    private boolean hasFiatBalance;

    private boolean hasActiveBalance;

    private boolean buyPatternFound;

    private boolean sellPatternFound;

    private boolean upperLimitReached;

    private boolean lowerLimitReached;
  }
}
