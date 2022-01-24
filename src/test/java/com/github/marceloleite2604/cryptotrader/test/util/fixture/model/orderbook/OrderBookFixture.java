package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orderbook;

import com.github.marceloleite2604.cryptotrader.model.orderbook.OrderBook;
import com.github.marceloleite2604.cryptotrader.model.orderbook.OrderBookItem;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class OrderBookFixture {

  public static final OffsetDateTime TIMESTAMP = OffsetDateTime.of(
    LocalDateTime.of(2022, 1, 20, 21, 11, 16),
    ZoneOffset.UTC);
  public static final List<OrderBookItem> ASKS = List.of(OrderBookItemFixture.createAsk());
  public static final List<OrderBookItem> BIDS = List.of(OrderBookItemFixture.createBid());

  public static OrderBook create() {
    return create(ASKS, BIDS);
  }

  public static OrderBook createWithEmptyOrderBookItems() {
    return create(Collections.emptyList(), Collections.emptyList());
  }

  private static OrderBook create(List<OrderBookItem> asks, List<OrderBookItem> bids) {
    return OrderBook.builder()
      .asks(asks)
      .bids(bids)
      .timestamp(TIMESTAMP)
      .build();
  }
}
