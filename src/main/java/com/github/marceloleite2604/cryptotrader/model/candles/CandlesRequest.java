package com.github.marceloleite2604.cryptotrader.model.candles;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CandlesRequest {

  private final String symbol;

  private final CandlePrecision resolution;

  private final OffsetDateTime toTime;

  private final Integer toCount;

  private final OffsetDateTime from;

  private final Integer countback;
}
