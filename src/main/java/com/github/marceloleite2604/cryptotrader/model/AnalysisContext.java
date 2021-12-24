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
}
