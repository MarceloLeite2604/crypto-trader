package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.trades;

import com.github.marceloleite2604.cryptotrader.model.trades.TradesRequest;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.GetSymbolsResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.trades.TradeDtoFixture;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class TradesRequestFixture {

  public static final long SINCE = 23871574L;

  public static final OffsetDateTime FROM = OffsetDateTime.of(
    LocalDateTime.of(2022, 1, 28, 21, 27, 31),
    ZoneOffset.UTC);

  public static final OffsetDateTime TO = OffsetDateTime.of(
    LocalDateTime.of(2022, 1, 28, 21, 28, 17),
    ZoneOffset.UTC);

  public static TradesRequest create() {
    return TradesRequest.builder()
      .symbol(GetSymbolsResponsePayloadFixture.SYMBOL_VALUE)
      .tid(TradeDtoFixture.TID)
      .since(SINCE)
      .from(FROM)
      .to(TO)
      .build();
  }
}
