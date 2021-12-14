package com.github.marceloleite2604.cryptotrader.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Getter
public class PositionDto {

  private final double avgPrice;

  private final String id;

  private final String instrument;

  private final double quantity;

  private final String side;
}
