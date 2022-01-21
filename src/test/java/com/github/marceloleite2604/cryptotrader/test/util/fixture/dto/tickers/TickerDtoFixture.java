package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.tickers;

import com.github.marceloleite2604.cryptotrader.dto.tickers.TickerDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TickerDtoFixture {

  public static final String BUY = "buyValue";
  public static final long DATE = 1642730485L;
  public static final String HIGH = "highValue";
  public static final String LAST = "lastValue";
  public static final String LOW = "lowValue";
  public static final String OPEN = "openValue";
  public static final String PAIR = "pairValue";
  public static final String VOL = "volValue";
  public static final String SELL = "sellValue";

  public static TickerDto create() {
    return TickerDto.builder()
      .buy(BUY)
      .date(DATE)
      .high(HIGH)
      .last(LAST)
      .low(LOW)
      .open(OPEN)
      .pair(PAIR)
      .vol(VOL)
      .sell(SELL)
      .build();
  }
}
