package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.account;

import com.github.marceloleite2604.cryptotrader.model.account.Position;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.PositionDtoFixture;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PositionFixture {

  public static Position create() {
    return Position.builder()
      .averagePrice(BigDecimal.valueOf(PositionDtoFixture.AVG_PRICE))
      .id(PositionDtoFixture.ID)
      .instrument(PositionDtoFixture.INSTRUMENT)
      .quantity(BigDecimal.valueOf(PositionDtoFixture.QUANTITY))
      .side(PositionDtoFixture.SIDE)
      .build();
  }
}
