package com.github.marceloleite2604.cryptotrader.dto.orders;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor(force = true)
@Getter
public class PostOrderRequestPayload {

  private final Boolean async;

  private final Double cost;

  private final Double limitPrice;

  private final Double qty;

  private final String side;

  private final String type;
}
