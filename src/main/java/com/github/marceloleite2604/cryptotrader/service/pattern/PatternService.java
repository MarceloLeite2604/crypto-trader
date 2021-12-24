package com.github.marceloleite2604.cryptotrader.service.pattern;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import com.github.marceloleite2604.cryptotrader.repository.PatternMatchRepository;
import com.github.marceloleite2604.cryptotrader.service.pattern.links.PatternChecker;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import com.github.marceloleite2604.cryptotrader.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class PatternService {

  private final PatternChecker first;

  private final PatternMatchRepository patternMatchRepository;

  private final ValidationUtil validationUtil;

  private final DateTimeUtil dateTimeUtil;

  public PatternService(
    List<PatternChecker> patternCheckers,
    PatternMatchRepository patternMatchRepository,
    ValidationUtil validationUtil,
    DateTimeUtil dateTimeUtil) {
    this.first = createChain(patternCheckers);
    this.patternMatchRepository = patternMatchRepository;
    this.validationUtil = validationUtil;
    this.dateTimeUtil = dateTimeUtil;
  }

  private PatternChecker createChain(List<PatternChecker> patternCheckers) {
    PatternChecker first = null;
    PatternChecker previous = null;

    for (PatternChecker patternChecker : patternCheckers) {
      if (first == null) {
        first = patternChecker;
      } else {
        previous.setNext(patternChecker);
      }
      previous = patternChecker;
    }

    return first;
  }

  public List<PatternMatch> check(Active active, List<Candle> candles) {
    if (CollectionUtils.isEmpty(candles)) {
      return Collections.emptyList();
    }

    if (first == null) {
      return Collections.emptyList();
    }

    final List<PatternMatch> alreadyDiscoveredPatternMatches = retrieveAlreadyDiscoveredPatternMatches(active, candles);

    if (CollectionUtils.isNotEmpty(alreadyDiscoveredPatternMatches)) {
      log.debug("Patterns already found. Ignoring analysis.");
      return Collections.emptyList();
    }

    var patternCheckContext = PatternCheckContext.builder()
      .candles(candles)
      .build();

    candles.sort(Collections.reverseOrder());
    patternCheckContext = first.check(patternCheckContext);
    Collections.sort(candles);

    return patternMatchRepository.saveAll(patternCheckContext.getPatternMatches());
  }

  private List<PatternMatch> retrieveAlreadyDiscoveredPatternMatches(Active active, List<Candle> candles) {
    final var firstCandle = candles.get(0);

    final var precision = firstCandle.getPrecision();

    final var end = dateTimeUtil.truncateTo(OffsetDateTime.now(ZoneOffset.UTC), precision.getDuration());

    final var start = end.minus(precision.getDuration());

    final var findPatterMatchesRequest = FindPatterMatchesRequest.builder()
      .active(active)
      .candlePrecision(precision)
      .start(start)
      .end(end)
      .build();

    return findPatternMatches(findPatterMatchesRequest);
  }

  private List<PatternMatch> findPatternMatches(FindPatterMatchesRequest findPatterMatchesRequest) {
    validationUtil.throwIllegalArgumentExceptionIfNotValid(
      findPatterMatchesRequest,
      "Arguments to find pattern matches as invalid.");

    return patternMatchRepository.findByActiveAndCandlePrecisionAndCandleTimeBetween(
      findPatterMatchesRequest.getActive(),
      findPatterMatchesRequest.getCandlePrecision(),
      findPatterMatchesRequest.getStart(),
      findPatterMatchesRequest.getEnd()
    );
  }
}
