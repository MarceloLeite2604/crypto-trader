package com.github.marceloleite2604.cryptotrader.dto.orders;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Getter
public class PostOrderResponsePayload {

  private final String orderId;
}
