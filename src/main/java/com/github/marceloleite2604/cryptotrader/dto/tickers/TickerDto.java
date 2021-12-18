package com.github.marceloleite2604.cryptotrader.dto.tickers;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Getter
public class TickerDto {

  private final String buy;

  private final long date;

  private final String high;

  private final String last;

  private final String low;

  private final String open;

  private final String pair;

  private final String sell;

  private final String vol;
}
