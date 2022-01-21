package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.trades;

import com.github.marceloleite2604.cryptotrader.dto.trades.TradeDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class TradeDtoFixture {

  public static final double AMOUNT = 2156.7432d;
  public static final long DATE = 1642730663L;
  public static final double PRICE = 82347.1235657d;
  public static final long TID = 1252215L;
  public static final String TYPE = "typeValue";

  public static TradeDto create() {
    return TradeDto.builder()
      .amount(AMOUNT)
      .date(DATE)
      .price(PRICE)
      .tid(TID)
      .type(TYPE)
      .build();
  }
}
