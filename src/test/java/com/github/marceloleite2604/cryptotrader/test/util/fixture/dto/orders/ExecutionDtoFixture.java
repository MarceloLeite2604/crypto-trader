package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders;

import com.github.marceloleite2604.cryptotrader.dto.orders.ExecutionDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExecutionDtoFixture {

  public static final String ID = "executionDtoIdValue";
  public static final int EXECUTED_AT = 1642713732;
  public static final String INSTRUMENT = "instrumentValue";
  public static final double PRICE = 12935.86575d;
  public static final double QTY = 817382.5672d;
  public static final String SIDE = "sideValue";

  public static ExecutionDto create() {
    return ExecutionDto.builder()
      .id(ID)
      .executedAt(EXECUTED_AT)
      .instrument(INSTRUMENT)
      .price(PRICE)
      .qty(QTY)
      .side(SIDE)
      .build();
  }
}
