package com.github.marceloleite2604.cryptotrader.dto.orders;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
public class PostOrderResponsePayload {

  private final String orderId;
}
