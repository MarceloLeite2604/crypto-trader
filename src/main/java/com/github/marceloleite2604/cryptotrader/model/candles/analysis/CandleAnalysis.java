package com.github.marceloleite2604.cryptotrader.model.candles.analysis;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CandleAnalysis implements Comparable<CandleAnalysis> {

  public CandleAnalysis(CandleAnalysis other) {
    this.candle = new Candle(other.candle);

    this.size = other.size;

    this.bodySize = other.bodySize;

    this.upperWickSize = other.upperWickSize;

    this.lowerWickSize = other.lowerWickSize;

    this.upperWickPercentage = other.upperWickPercentage;

    this.lowerWickPercentage = other.lowerWickPercentage;

    this.bodyPercentage = other.bodyPercentage;

    this.average = other.average;

    this.bodyAverage = other.bodyAverage;

    this.direction = other.direction;

    this.type = other.type;

    this.candleSizeCategory = other.candleSizeCategory;

    this.bodySizeCategory = other.bodySizeCategory;

    this.done = other.done;

    this.position = other.position;
  }

  @EqualsAndHashCode.Include
  @NotNull(message = "Candle cannot be null.")
  private final Candle candle;

  private final BigDecimal size;

  private final BigDecimal bodySize;

  private final BigDecimal upperWickSize;

  private final BigDecimal lowerWickSize;

  private final BigDecimal upperWickPercentage;

  private final BigDecimal lowerWickPercentage;

  private final BigDecimal bodyPercentage;

  private final BigDecimal average;

  private final BigDecimal bodyAverage;

  private final CandleDirection direction;

  private final CandleType type;

  @Setter
  private CandlePosition position;

  @Setter
  private CandleSizeCategory candleSizeCategory;

  @Setter
  private CandleSizeCategory bodySizeCategory;

  @Setter
  private boolean done;

  @Override
  public int compareTo(CandleAnalysis other) {
    return this.candle.compareTo(other.candle);
  }
}
