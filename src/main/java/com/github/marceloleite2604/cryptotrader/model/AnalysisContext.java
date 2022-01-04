package com.github.marceloleite2604.cryptotrader.model;

import com.github.marceloleite2604.cryptotrader.model.account.Balance;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import com.github.marceloleite2604.cryptotrader.model.profit.Profit;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Stream;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AnalysisContext {

  private final Active active;

  private final List<Candle> longRangeCandles;

  private final List<Candle> shortRangeCandles;

  private final List<PatternMatch> longRangePatterns;

  private final List<PatternMatch> shortRangePatterns;

  private final Balance activeBalance;

  private final Balance fiatBalance;

  private final Profit profit;

  private Boolean sellPatternFound;

  private Boolean buyPatternFound;

  public List<PatternMatch> retrieveBuyPatternMatches() {
    return retrievePatternMatchesBySide(Side.BUY);
  }

  public List<PatternMatch> retrieveSellPatternMatches() {
    return retrievePatternMatchesBySide(Side.SELL);
  }

  public List<PatternMatch> retrievePatternMatchesBySide(Side side) {
    return Stream.concat(shortRangePatterns.stream(), longRangePatterns.stream())
      .filter(patternMatch -> side.equals(patternMatch.getType()
        .getSide()))
      .toList();
  }

  public boolean isSellPatternFound() {
    if (sellPatternFound == null) {
      sellPatternFound = retrieveSellPatternMatches()
        .size() > 0;
    }
    return sellPatternFound;
  }

  public boolean isBuyPatternFound() {
    if (buyPatternFound == null) {
      buyPatternFound = retrieveBuyPatternMatches()
        .size() > 0;
    }
    return buyPatternFound;
  }

  public boolean isFiatBalanceAvailable() {
    return fiatBalance.isNotEmpty();
  }

  public boolean isActiveBalanceAvailable() {
    return activeBalance.isNotEmpty();
  }

  public boolean isOverUpperLimit() {
    return profit.hasReachedUpperLimit();
  }

  public boolean isBelowLowerLimit() {
    return profit.hasReachedLowerLimit();
  }

  public String toString() {
    return AnalysisContext.class.getSimpleName() +
      "(isFiatBalanceAvailable=" + isFiatBalanceAvailable() + ", " +
      "isActiveBalanceAvailable=" + isActiveBalanceAvailable() + ", " +
      "isBuyPatternFound=" + isBuyPatternFound() + ", " +
      "isSellPatternFound=" + isSellPatternFound() + ", " +
      "isBelowLowerLimit=" + isBelowLowerLimit() +
      "isOverUpperLimit=" + isOverUpperLimit() + ")";
  }
}
