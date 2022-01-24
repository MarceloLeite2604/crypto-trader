package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.tickers;

import com.github.marceloleite2604.cryptotrader.dto.tickers.TickerDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TickerDtoFixture {

  public static final String BUY = "57983.366";
  public static final long DATE = 1642730485L;
  public static final String HIGH = "1553.36";
  public static final String LAST = "2672.44";
  public static final String LOW = "298520.55";
  public static final String OPEN = "4372.23986";
  public static final String PAIR = "pairValue";
  public static final String VOL = "10985.146";
  public static final String SELL = "8236.25";

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
