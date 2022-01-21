package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders;

import com.github.marceloleite2604.cryptotrader.dto.orders.PostOrderRequestPayload;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PostOrderRequestPayloadFixture {

  public static final String TYPE = "typeValue";
  public static final String SIDE = "sideValue";
  public static final boolean ASYNC = true;
  public static final double COST = 215863.1235d;
  public static final double LIMIT_PRICE = 62095.113d;
  public static final double QTY = 0.2582d;

  public static PostOrderRequestPayload create() {
    return PostOrderRequestPayload.builder()
      .type(TYPE)
      .side(SIDE)
      .async(ASYNC)
      .cost(COST)
      .limitPrice(LIMIT_PRICE)
      .qty(QTY)
      .build();
  }
}
