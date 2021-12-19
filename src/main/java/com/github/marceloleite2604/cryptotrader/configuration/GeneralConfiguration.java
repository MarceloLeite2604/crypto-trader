package com.github.marceloleite2604.cryptotrader.configuration;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class GeneralConfiguration {
  public static final RoundingMode DEFAULT_ROUNDING_MODE = RoundingMode.HALF_EVEN;

  public static final BigDecimal COMPARISON_THRESHOLD = BigDecimal.valueOf(0.05);
}
