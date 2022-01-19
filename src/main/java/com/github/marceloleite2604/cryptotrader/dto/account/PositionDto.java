package com.github.marceloleite2604.cryptotrader.dto.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
public class PositionDto {

  private final double avgPrice;

  private final String id;

  private final String instrument;

  private final double quantity;

  private final String side;
}
