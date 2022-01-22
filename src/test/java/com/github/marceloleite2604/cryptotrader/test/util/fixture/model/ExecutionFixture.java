package com.github.marceloleite2604.cryptotrader.test.util.fixture.model;

import com.github.marceloleite2604.cryptotrader.model.orders.Execution;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.ExecutionDtoFixture;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class ExecutionFixture {

  public static final BigDecimal PRICE = BigDecimal.valueOf(ExecutionDtoFixture.PRICE);
  public static final BigDecimal QUANTITY = BigDecimal.valueOf(ExecutionDtoFixture.QTY);
  public static final OffsetDateTime EXECUTED_AT = OffsetDateTime.of(
    LocalDateTime.of(2022, 1, 20, 21, 11, 16),
    ZoneOffset.UTC);

  public static Execution create() {
    return Execution.builder()
      .id(ExecutionDtoFixture.ID)
      .side(ExecutionDtoFixture.SIDE)
      .price(PRICE)
      .instrument(ExecutionDtoFixture.INSTRUMENT)
      .quantity(QUANTITY)
      .executedAt(EXECUTED_AT)
      .build();
  }
}
