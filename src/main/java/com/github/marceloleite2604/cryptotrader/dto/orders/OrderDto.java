package com.github.marceloleite2604.cryptotrader.dto.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class OrderDto {

  private final double avgPrice;

  @JsonProperty("created_at")
  private final long createdAt;

  private final List<ExecutionDto> executions;

  private final double filledQty;

  private final String id;

  private final String instrument;

  private final double limitPrice;

  private final double qty;

  private final String side;

  private final String status;

  private final String type;

  @JsonProperty("updated_at")
  private final long updatedAt;

  private final double fee;
}
