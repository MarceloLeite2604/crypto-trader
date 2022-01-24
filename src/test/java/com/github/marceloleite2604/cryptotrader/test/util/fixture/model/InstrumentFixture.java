package com.github.marceloleite2604.cryptotrader.test.util.fixture.model;

import com.github.marceloleite2604.cryptotrader.model.Instrument;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.GetSymbolsResponsePayloadFixture;
import lombok.experimental.UtilityClass;

import java.util.TimeZone;

@UtilityClass
public class InstrumentFixture {

  public static final TimeZone TIMEZONE = TimeZone.getTimeZone(GetSymbolsResponsePayloadFixture.TIMEZONE_VALUE);

  public static Instrument create() {
    return Instrument.builder()
      .type(GetSymbolsResponsePayloadFixture.TYPE_VALUE)
      .symbol(GetSymbolsResponsePayloadFixture.SYMBOL_VALUE)
      .sessionRegular(GetSymbolsResponsePayloadFixture.SESSION_REGULAR_VALUE)
      .priceScale(GetSymbolsResponsePayloadFixture.PRICE_SCALE_VALUE)
      .minMovement(GetSymbolsResponsePayloadFixture.MIN_MOVEMENT_VALUE)
      .exchangeListed(GetSymbolsResponsePayloadFixture.EXCHANGE_LISTED_VALUE)
      .baseCurrency(GetSymbolsResponsePayloadFixture.BASE_CURRENCY_VALUE)
      .currency(GetSymbolsResponsePayloadFixture.CURRENCY_VALUE)
      .description(GetSymbolsResponsePayloadFixture.DESCRIPTION_VALUE)
      .exchangeTraded(GetSymbolsResponsePayloadFixture.EXCHANGE_TRADED_VALUE)
      .timezone(TIMEZONE)
      .build();
  }
}
