package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orderbook;

import com.github.marceloleite2604.cryptotrader.dto.orderbook.GetOrderBookResponsePayload;
import lombok.experimental.UtilityClass;

import java.util.Collections;
import java.util.List;

@UtilityClass
public class GetOrderBookResponsePayloadFixture {

  public static final double ASK_PRICE = 12356.1236d;
  public static final double ASK_QUANTITY = 7252.215d;

  public static final double BID_PRICE = 1049.19495d;
  public static final double BID_QUANTITY = 14883.263d;

  public static final double[] ASKS_VALUES = {ASK_PRICE, ASK_QUANTITY};
  public static final double[] BIDS_VALUES = {BID_PRICE, BID_QUANTITY};

  public static final long TIMESTAMP = 1642713467;

  public static final List<double[]> ASKS = List.of(ASKS_VALUES);
  public static final List<double[]> BIDS = List.of(BIDS_VALUES);

  public static GetOrderBookResponsePayload create() {
    return create(ASKS, BIDS);
  }

  public static GetOrderBookResponsePayload createWithNoAsksAndBids() {
    return create(Collections.emptyList(), Collections.emptyList());
  }

  public static GetOrderBookResponsePayload create(List<double[]> asks, List<double[]> bids) {
    return GetOrderBookResponsePayload.builder()
      .asks(asks)
      .bids(bids)
      .timestamp(TIMESTAMP)
      .build();
  }
}
