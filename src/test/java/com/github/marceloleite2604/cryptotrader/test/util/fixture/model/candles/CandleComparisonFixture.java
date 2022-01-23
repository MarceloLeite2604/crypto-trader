package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.candles;

import com.github.marceloleite2604.cryptotrader.model.candles.CandleComparison;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePosition;
import com.github.marceloleite2604.cryptotrader.model.candles.CandleProportion;
import lombok.experimental.UtilityClass;

@UtilityClass
public class CandleComparisonFixture {

  public static final CandlePosition POSITION = CandlePosition.RAISED;
  public static final CandleProportion CANDLE_PROPORTION = CandleProportion.MEDIUM_LARGE;
  public static final CandleProportion BODY_PROPORTION = CandleProportion.MEDIUM_SMALL;
  public static final CandleProportion VOLUME_PROPORTION = CandleProportion.LARGE;

  public static CandleComparison create() {
    return CandleComparison.builder()
      .position(POSITION)
      .candleProportion(CANDLE_PROPORTION)
      .bodyProportion(BODY_PROPORTION)
      .volumeProportion(VOLUME_PROPORTION)
      .build();
  }
}
