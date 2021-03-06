package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account;

import com.github.marceloleite2604.cryptotrader.dto.account.BalanceDto;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.ActiveFixture;
import lombok.experimental.UtilityClass;

@UtilityClass
public class BalanceDtoFixture {

  public static final double AVAILABLE = 23546.73;
  public static final double TOTAL = 6735432412.23765;

  public static BalanceDto create() {
    return BalanceDto.builder()
      .available(AVAILABLE)
      /* API returns base on symbol property. */
      .symbol(ActiveFixture.BASE)
      .total(TOTAL)
      .build();
  }
}
