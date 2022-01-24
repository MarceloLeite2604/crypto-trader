package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orders;

import com.github.marceloleite2604.cryptotrader.model.orders.PlaceOrderRequest;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.PostOrderRequestPayloadFixture;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PlaceOrderRequestFixture {

  public static final String ACCOUNT_ID = "accountIdValue";
  public static final String SYMBOL = "symbolValue";
  public static final BigDecimal COST = BigDecimal.valueOf(PostOrderRequestPayloadFixture.COST);
  public static final BigDecimal LIMIT_PRICE = BigDecimal.valueOf(PostOrderRequestPayloadFixture.LIMIT_PRICE);
  public static final BigDecimal QUANTITY = BigDecimal.valueOf(PostOrderRequestPayloadFixture.QTY);

  public static PlaceOrderRequest create() {
    return PlaceOrderRequest.builder()
      .accountId(ACCOUNT_ID)
      .symbol(SYMBOL)
      .async(PostOrderRequestPayloadFixture.ASYNC)
      .cost(COST)
      .limitPrice(LIMIT_PRICE)
      .quantity(QUANTITY)
      .side(PostOrderRequestPayloadFixture.SIDE)
      .type(PostOrderRequestPayloadFixture.TYPE)
      .build();
  }
}
