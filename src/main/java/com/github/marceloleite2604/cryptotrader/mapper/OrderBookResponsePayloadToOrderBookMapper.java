package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.dto.orderbook.OrderBookResponsePayload;
import com.github.marceloleite2604.cryptotrader.model.orderbook.Order;
import com.github.marceloleite2604.cryptotrader.model.orderbook.OrderBook;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderBookResponsePayloadToOrderBookMapper
  implements Mapper<OrderBookResponsePayload, OrderBook> {

  private final DateTimeUtil dateTimeUtil;

  @Override
  public OrderBook mapTo(OrderBookResponsePayload orderBookResponsePayload) {

    final var asks = createOrders(orderBookResponsePayload.getAsks());
    final var bids = createOrders(orderBookResponsePayload.getBids());
    final var timestamp = dateTimeUtil.convertTimestampWithNanosToUtcOffsetDateTime(
      orderBookResponsePayload.getTimestamp());

    return OrderBook.builder()
      .asks(asks)
      .bids(bids)
      .timestamp(timestamp)
      .build();
  }

  private List<Order> createOrders(List<double[]> rawOrders) {
    if (CollectionUtils.isEmpty(rawOrders)) {
      return Collections.emptyList();
    }

    List<Order> orders = new ArrayList<>(rawOrders.size());

    for (double[] rawOrder : rawOrders) {
      final var price = BigDecimal.valueOf(rawOrder[0]);
      final var volume = BigDecimal.valueOf(rawOrder[1]);
      final var order = Order.builder()
        .price(price)
        .volume(volume)
        .build();

      orders.add(order);
    }

    return orders;
  }
}
