package com.github.marceloleite2604.cryptotrader.model.orderbook;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class OrderBook {

  private List<Order> asks;

  private List<Order> bids;

  private OffsetDateTime timestamp;
}
