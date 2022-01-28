package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orders;

import com.github.marceloleite2604.cryptotrader.model.orders.PlaceOrderRequest;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.AccountDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.GetSymbolsResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.PostOrderRequestPayloadFixture;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class PlaceOrderRequestFixture {

  public static final BigDecimal COST = BigDecimal.valueOf(PostOrderRequestPayloadFixture.COST);
  public static final BigDecimal LIMIT_PRICE = BigDecimal.valueOf(PostOrderRequestPayloadFixture.LIMIT_PRICE);
  public static final BigDecimal QUANTITY = BigDecimal.valueOf(PostOrderRequestPayloadFixture.QTY);

  public static PlaceOrderRequest create() {
    return PlaceOrderRequest.builder()
      .accountId(AccountDtoFixture.ID)
      .symbol(GetSymbolsResponsePayloadFixture.SYMBOL_VALUE)
      .async(PostOrderRequestPayloadFixture.ASYNC)
      .cost(COST)
      .limitPrice(LIMIT_PRICE)
      .quantity(QUANTITY)
      .side(PostOrderRequestPayloadFixture.SIDE)
      .type(PostOrderRequestPayloadFixture.TYPE)
      .build();
  }
}
