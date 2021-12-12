package com.github.marceloleite2604.cryptotrader.dto.symbols;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(force = true)
public class SymbolResponsePayload {

  @JsonProperty("base-currency")
  private final List<String> baseCurrency;

  private final List<String> currency;

  private final List<String> description;

  @JsonProperty("exchange-listed")
  private final List<Boolean> exchangeListed;

  @JsonProperty("exchange-traded")
  private final List<Boolean> exchangeTraded;

  @JsonProperty("minmovement")
  private final List<Long> minMovement;

  @JsonProperty("pricescale")
  private final List<Long> priceScale;

  @JsonProperty("session-regular")
  private final List<String> sessionRegular;

  private final List<String> symbol;

  private final List<String> timezone;

  private final List<String> type;
}
