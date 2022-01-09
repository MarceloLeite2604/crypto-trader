package com.github.marceloleite2604.cryptotrader.util;

import com.github.marceloleite2604.cryptotrader.configuration.GeneralConfiguration;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ComparisonUtil {

  public int compareRatioUsingMargin(BigDecimal first, BigDecimal second) {
    return compareRatioUsingMargin(first, second, GeneralConfiguration.COMPARISON_THRESHOLD);
  }

  public int compareRatioUsingMargin(BigDecimal first, BigDecimal second, BigDecimal margin) {

    final var ratio = second
      .compareTo(BigDecimal.ZERO) == 0 ?
      BigDecimal.ZERO : first.divide(second, GeneralConfiguration.DEFAULT_ROUNDING_MODE);

    if (ratio.compareTo(margin) > 0) {
      return 1;
    } else if (ratio.compareTo(margin.negate()) < 0) {
      return -1;
    }

    return 0;
  }
}
