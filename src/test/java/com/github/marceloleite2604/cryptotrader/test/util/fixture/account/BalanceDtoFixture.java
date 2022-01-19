package com.github.marceloleite2604.cryptotrader.test.util.fixture.account;

import com.github.marceloleite2604.cryptotrader.dto.account.BalanceDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BalanceDtoFixture {

  public static final double AVAILABLE = 23546.73;
  public static final String SYMBOL = "symbolValue";
  public static final double TOTAL = 6735432412.23765;

  public static BalanceDto create() {
    return BalanceDto.builder()
      .available(AVAILABLE)
      .symbol(SYMBOL)
      .total(TOTAL)
      .build();
  }
}
