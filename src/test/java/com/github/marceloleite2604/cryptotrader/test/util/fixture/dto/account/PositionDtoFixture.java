package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account;

import com.github.marceloleite2604.cryptotrader.dto.account.PositionDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PositionDtoFixture {

  public static final String ID = "positionDtoIdValue";
  public static final double AVG_PRICE = 234.11425;
  public static final String INSTRUMENT = "instrumentValue";
  public static final double QUANTITY = 26743.254782;
  public static final String SIDE = "sideValue";

  public static PositionDto create() {
    return PositionDto.builder()
      .id(ID)
      .avgPrice(AVG_PRICE)
      .instrument(INSTRUMENT)
      .quantity(QUANTITY)
      .side(SIDE)
      .build();
  }
}
