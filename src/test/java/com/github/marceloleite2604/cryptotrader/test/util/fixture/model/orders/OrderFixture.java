package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orders;

import com.github.marceloleite2604.cryptotrader.model.orders.Execution;
import com.github.marceloleite2604.cryptotrader.model.orders.Order;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.OrderDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.ExecutionFixture;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@UtilityClass
public class OrderFixture {

  public static final BigDecimal AVERAGE_PRICE = BigDecimal.valueOf(OrderDtoFixture.AVG_PRICE);
  public static final OffsetDateTime CREATED_AT = OffsetDateTime.of(LocalDateTime.of(2022, 1, 21, 1, 43, 38), ZoneOffset.UTC);
  public static final List<Execution> EXECUTIONS = List.of(ExecutionFixture.create());
  public static final BigDecimal FILLED_QUANTITY = BigDecimal.valueOf(OrderDtoFixture.FILLED_QTY);
  public static final BigDecimal LIMIT_PRICE = BigDecimal.valueOf(OrderDtoFixture.LIMIT_PRICE);
  public static final BigDecimal QUANTITY = BigDecimal.valueOf(OrderDtoFixture.QTY);
  public static final OffsetDateTime UPDATED_AT = OffsetDateTime.of(LocalDateTime.of(2022, 1, 21, 1, 44, 45), ZoneOffset.UTC);

  public static Order create() {
    return Order.builder()
      .averagePrice(AVERAGE_PRICE)
      .createdAt(CREATED_AT)
      .executions(EXECUTIONS)
      .filledQuantity(FILLED_QUANTITY)
      .id(OrderDtoFixture.ID)
      .instrument(OrderDtoFixture.INSTRUMENT_VALUE)
      .limitPrice(LIMIT_PRICE)
      .quantity(QUANTITY)
      .side(OrderDtoFixture.SIDE)
      .status(OrderDtoFixture.STATUS)
      .type(OrderDtoFixture.TYPE)
      .updatedAt(UPDATED_AT)
      .build();
  }
}
