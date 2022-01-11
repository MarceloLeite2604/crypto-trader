package com.github.marceloleite2604.cryptotrader.model.orders;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class Order implements Comparable<Order> {

  private final BigDecimal averagePrice;

  private final OffsetDateTime createdAt;

  private final List<Execution> executions;

  private final BigDecimal filledQuantity;

  private final String id;

  private final String instrument;

  private final BigDecimal limitPrice;

  private final BigDecimal quantity;

  private final String side;

  private final String status;

  private final String type;

  private final OffsetDateTime updatedAt;

  @Override
  public int compareTo(Order other) {
    return this.createdAt.compareTo(other.createdAt);
  }
}
