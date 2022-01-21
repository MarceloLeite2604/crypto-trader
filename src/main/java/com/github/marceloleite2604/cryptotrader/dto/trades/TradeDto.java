package com.github.marceloleite2604.cryptotrader.dto.trades;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
public class TradeDto {

  private final double amount;

  private final long date;

  private final double price;

  private final long tid;

  private final String type;

}
