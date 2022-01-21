package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orderbook;

import com.github.marceloleite2604.cryptotrader.dto.orderbook.GetOrderBookResponsePayload;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class GetOrderBookResponsePayloadFixture {

  public static final double[] ASKS_VALUES = {12356.1236d};
  public static final List<double[]> ASKS = List.of(ASKS_VALUES);
  public static final double[] BIDS_VALUES = {1049.19495d};
  public static final List<double[]> BIDS = List.of(BIDS_VALUES);
  public static final long TIMESTAMP = 1642713467;

  public static GetOrderBookResponsePayload create() {
    return GetOrderBookResponsePayload.builder()
      .asks(ASKS)
      .bids(BIDS)
      .timestamp(TIMESTAMP)
      .build();
  }
}
