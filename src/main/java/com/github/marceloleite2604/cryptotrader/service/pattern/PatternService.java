package com.github.marceloleite2604.cryptotrader.service.pattern;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import com.github.marceloleite2604.cryptotrader.service.pattern.links.Pattern;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PatternService {

  private final Pattern first;

  public PatternService(List<Pattern> patterns) {
    this.first = createChain(patterns);
  }

  private Pattern createChain(List<Pattern> patterns) {
    Pattern first = null;
    Pattern previous = null;

    for (Pattern pattern : patterns) {
      if (first == null) {
        first = pattern;
      } else {
        previous.setNext(pattern);
      }
      previous = pattern;
    }

    return first;
  }

  public List<PatternMatch> check(List<Candle> candles) {
    if (CollectionUtils.isEmpty(candles)) {
      return Collections.emptyList();
    }

    if (first == null) {
      return Collections.emptyList();
    }


    var patternCheckContext = PatternCheckContext.builder()
      .candles(candles)
      .build();

    candles.sort(Collections.reverseOrder());
    patternCheckContext = first.check(patternCheckContext);
    Collections.sort(candles);

    return patternCheckContext.getPatternMatches();
  }
}
