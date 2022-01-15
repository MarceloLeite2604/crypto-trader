package com.github.marceloleite2604.cryptotrader.model.pattern;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
@ToString
public class PatternMatch {

  private final PatternType type;

  private final OffsetDateTime candleTime;

  private final CandlePrecision candlePrecision;

  private final Active active;
}
