package com.github.marceloleite2604.cryptotrader.dto;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(force = true)
@Getter
public class ErrorMessageDto {

  private final String code;

  private final String message;

  private Map<String, String> additionalProperties;

  @JsonAnySetter
  public void add(String key, String value) {
    if (additionalProperties == null) {
      additionalProperties = new HashMap<>();
    }
    additionalProperties.put(key, value);
  }
}
