package com.github.marceloleite2604.cryptotrader.model.patterns;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class PatternMatch {

  private final PatternType type;

  private final OffsetDateTime candleTime;

  private final String symbol;
}
