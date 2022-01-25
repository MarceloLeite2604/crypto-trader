package com.github.marceloleite2604.cryptotrader.dto.orders;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
public class ExecutionDto {

  @JsonProperty("executed_at")
  private final long executedAt;

  private final String id;

  private final String instrument;

  private final double price;

  private final double qty;

  private final String side;

  private Map<String, String> additionalProperties;

  @JsonAnySetter
  public void add(String key, String value) {
    if (additionalProperties == null) {
      additionalProperties = new HashMap<>();
    }
    additionalProperties.put(key, value);
  }
}
