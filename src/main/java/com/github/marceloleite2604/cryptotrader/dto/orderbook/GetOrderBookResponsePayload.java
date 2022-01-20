package com.github.marceloleite2604.cryptotrader.dto.orderbook;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
public class GetOrderBookResponsePayload {

  private final List<double[]> asks;

  private final List<double[]> bids;

  private final long timestamp;
}
