package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.account;

import com.github.marceloleite2604.cryptotrader.model.account.Balance;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.BalanceDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.ActiveFixture;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class BalanceFixture {

  public static final BigDecimal AVAILABLE = BigDecimal.valueOf(BalanceDtoFixture.AVAILABLE);
  public static final BigDecimal TOTAL = BigDecimal.valueOf(BalanceDtoFixture.TOTAL);

  public static Balance create() {
    return Balance.builder()
      .available(AVAILABLE)
      .active(ActiveFixture.retrieve())
      .total(TOTAL)
      .build();
  }
}
