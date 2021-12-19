package com.github.marceloleite2604.cryptotrader.model.pattern;

import com.github.marceloleite2604.cryptotrader.model.Side;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PatternType {
  HAMMER("Hammer", Side.BUY),
  INVERSE_HAMMER("Inverse Hammer", Side.BUY),
  RISING_PIERCING_LINE("Rising Piercing Line", Side.BUY),
  FALLING_PIERCING_LINE("Falling Piercing Line", Side.SELL);

  private final String name;

  private final Side side;
}
