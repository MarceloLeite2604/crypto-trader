package com.github.marceloleite2604.cryptotrader.service.pattern;

import com.github.marceloleite2604.cryptotrader.model.candles.analysis.CandleAnalysis;
import com.github.marceloleite2604.cryptotrader.model.patterns.PatternMatch;
import com.github.marceloleite2604.cryptotrader.service.pattern.links.Pattern;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PatternCheckService {

  private final Pattern first;

  public PatternCheckService(List<Pattern> patterns) {
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

  public List<PatternMatch> check(List<CandleAnalysis> candleAnalyses) {
    if (CollectionUtils.isEmpty(candleAnalyses)) {
      return Collections.emptyList();
    }

    if (first == null) {
      return Collections.emptyList();
    }

    candleAnalyses.sort(Collections.reverseOrder());
    var patternCheckContext = PatternCheckContext.builder()
      .candleAnalyses(candleAnalyses)
      .build();

    patternCheckContext = first.check(patternCheckContext);

    return patternCheckContext.getPatternMatches();
  }
}
