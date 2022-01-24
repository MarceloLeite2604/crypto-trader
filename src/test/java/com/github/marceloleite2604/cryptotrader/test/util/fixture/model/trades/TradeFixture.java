package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.trades;

import com.github.marceloleite2604.cryptotrader.model.trades.Trade;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.trades.TradeDtoFixture;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class TradeFixture {

  public static final OffsetDateTime DATE = OffsetDateTime.of(LocalDateTime.of(2022, 1, 21, 2, 4, 23), ZoneOffset.UTC);

  public static Trade create() {
    return Trade.builder()
      .amount(BigDecimal.valueOf(TradeDtoFixture.AMOUNT))
      .date(DATE)
      .price(BigDecimal.valueOf(TradeDtoFixture.PRICE))
      .tid(TradeDtoFixture.TID)
      .type(TradeDtoFixture.TYPE)
      .build();
  }

}
