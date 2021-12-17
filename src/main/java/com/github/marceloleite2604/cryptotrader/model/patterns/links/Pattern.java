package com.github.marceloleite2604.cryptotrader.model.patterns.links;

import com.github.marceloleite2604.cryptotrader.model.patterns.PatternCheckContext;

public interface Pattern {

  PatternCheckContext check(PatternCheckContext patternCheckContext);

  void setNext(Pattern pattern);
}
