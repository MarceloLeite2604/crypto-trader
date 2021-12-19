package com.github.marceloleite2604.cryptotrader.service.pattern;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleComparison;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PatternCheckContext {

  private final List<Candle> candles;

  private final List<PatternMatch> patternMatches = new ArrayList<>();

  public void addMatch(PatternMatch patternMatch) {
    patternMatches.add(patternMatch);
  }
}
