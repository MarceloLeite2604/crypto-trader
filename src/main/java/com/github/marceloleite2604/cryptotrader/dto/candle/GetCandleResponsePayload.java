package com.github.marceloleite2604.cryptotrader.dto.candle;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
public class GetCandleResponsePayload {

  @JsonAlias("c")
  private final List<Double> close;

  @JsonAlias("h")
  private final List<Double> high;

  @JsonAlias("l")
  private final List<Double> low;

  @JsonAlias("o")
  private final List<Double> open;

  @JsonAlias("t")
  private final List<Long> timestamp;

  @JsonAlias("v")
  private final List<Double> volume;

  private Map<String, String> additionalProperties;

  @JsonAnySetter
  public void add(String key, String value) {
    if (additionalProperties == null) {
      additionalProperties = new HashMap<>();
    }
    additionalProperties.put(key, value);
  }
}
