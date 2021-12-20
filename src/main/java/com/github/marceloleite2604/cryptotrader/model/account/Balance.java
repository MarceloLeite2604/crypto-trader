package com.github.marceloleite2604.cryptotrader.model.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Balance {

  private BigDecimal available;

  private String symbol;

  private BigDecimal total;

  public boolean isEmpty() {
    return total.compareTo(BigDecimal.valueOf(0.000001)) < 0;
  }
}
