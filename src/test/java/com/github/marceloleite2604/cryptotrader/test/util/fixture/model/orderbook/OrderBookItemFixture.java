package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orderbook;

import com.github.marceloleite2604.cryptotrader.model.orderbook.OrderBookItem;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orderbook.GetOrderBookResponsePayloadFixture;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;

@UtilityClass
public class OrderBookItemFixture {

  public static OrderBookItem createAsk() {
    return OrderBookItem.builder()
      .volume(BigDecimal.valueOf(GetOrderBookResponsePayloadFixture.ASK_QUANTITY))
      .price(BigDecimal.valueOf(GetOrderBookResponsePayloadFixture.ASK_PRICE))
      .build();
  }

  public static OrderBookItem createBid() {
    return OrderBookItem.builder()
      .volume(BigDecimal.valueOf(GetOrderBookResponsePayloadFixture.BID_QUANTITY))
      .price(BigDecimal.valueOf(GetOrderBookResponsePayloadFixture.BID_PRICE))
      .build();
  }
}
