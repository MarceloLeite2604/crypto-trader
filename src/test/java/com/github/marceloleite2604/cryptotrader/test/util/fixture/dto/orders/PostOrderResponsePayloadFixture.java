package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders;

import com.github.marceloleite2604.cryptotrader.dto.orders.PostOrderResponsePayload;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PostOrderResponsePayloadFixture {

  public static PostOrderResponsePayload create() {
    return PostOrderResponsePayload.builder()
      .orderId(OrderDtoFixture.ID)
      .build();
  }
}
