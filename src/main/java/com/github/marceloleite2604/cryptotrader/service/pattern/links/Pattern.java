package com.github.marceloleite2604.cryptotrader.service.pattern.links;

import com.github.marceloleite2604.cryptotrader.service.pattern.PatternCheckContext;

public interface Pattern {

  PatternCheckContext check(PatternCheckContext patternCheckContext);

  void setNext(Pattern pattern);
}
