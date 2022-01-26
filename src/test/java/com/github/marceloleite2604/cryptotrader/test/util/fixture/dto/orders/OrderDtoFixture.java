package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders;

import com.github.marceloleite2604.cryptotrader.dto.orders.ExecutionDto;
import com.github.marceloleite2604.cryptotrader.dto.orders.OrderDto;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class OrderDtoFixture {

  public static final String ID = "OrderDtoIdValue";
  public static final List<ExecutionDto> EXECUTIONS = List.of(ExecutionDtoFixture.create());
  public static final long CREATED_AT = 1642729418L;
  public static final double AVG_PRICE = 213.7453d;
  public static final double FILLED_QTY = 236.25d;
  public static final String INSTRUMENT_VALUE = "instrumentValue";
  public static final double LIMIT_PRICE = 79306.257d;
  public static final double QTY = 62206.205693d;
  public static final String SIDE = "sideValue";
  public static final String STATUS = "statusValue";
  public static final String TYPE = "typeValue";
  public static final long UPDATED_AT = 1642729485L;
  public static final double FEE = 592052.2051d;

  public static OrderDto create() {
    return OrderDto.builder()
      .id(ID)
      .executions(EXECUTIONS)
      .createdAt(CREATED_AT)
      .avgPrice(AVG_PRICE)
      .filledQty(FILLED_QTY)
      .instrument(INSTRUMENT_VALUE)
      .limitPrice(LIMIT_PRICE)
      .qty(QTY)
      .side(SIDE)
      .status(STATUS)
      .type(TYPE)
      .updatedAt(UPDATED_AT)
      .fee(FEE)
      .build();
  }
}
