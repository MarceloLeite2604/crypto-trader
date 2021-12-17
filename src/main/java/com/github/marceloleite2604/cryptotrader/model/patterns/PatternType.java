package com.github.marceloleite2604.cryptotrader.model.patterns;

import com.github.marceloleite2604.cryptotrader.model.Side;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public enum PatternType {
  HAMMER(Side.BUY),
  INVERSE_HAMMER(Side.BUY),
  RISING_PIERCING_LINE(Side.BUY),
  FALLING_PIERCING_LINE(Side.SELL);

  private final Side side;
}
