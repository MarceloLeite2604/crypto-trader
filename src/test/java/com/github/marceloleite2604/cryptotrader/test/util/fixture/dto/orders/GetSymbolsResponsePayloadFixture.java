package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders;

import com.github.marceloleite2604.cryptotrader.dto.symbols.GetSymbolsResponsePayload;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class GetSymbolsResponsePayloadFixture {

  public static final String BASE_CURRENCY_VALUE = "baseCurrencyValue";
  public static final String CURRENCY_VALUE = "currencyValue";
  public static final String DESCRIPTION_VALUE = "descriptionValue";
  public static final boolean EXCHANGE_LISTED_VALUE = false;
  public static final boolean EXCHANGE_TRADED_VALUE = true;
  public static final long MIN_MOVEMENT_VALUE = 34987512L;
  public static final long PRICE_SCALE_VALUE = 2364L;
  public static final String SESSION_REGULAR_VALUE = "sessionRegularValue";
  public static final String SYMBOL_VALUE = "symbolValue";
  public static final String TIMEZONE_VALUE = "timezoneValue";
  public static final String TYPE_VALUE = "typeValue";

  public static final List<String> BASE_CURRENCY = List.of(BASE_CURRENCY_VALUE);
  public static final List<String> CURRENCY = List.of(CURRENCY_VALUE);
  public static final List<String> DESCRIPTION = List.of(DESCRIPTION_VALUE);
  public static final List<Boolean> EXCHANGE_LISTED = List.of(EXCHANGE_LISTED_VALUE);
  public static final List<Boolean> EXCHANGE_TRADED = List.of(EXCHANGE_TRADED_VALUE);
  public static final List<Long> MIN_MOVEMENT = List.of(MIN_MOVEMENT_VALUE);
  public static final List<Long> PRICE_SCALE = List.of(PRICE_SCALE_VALUE);
  public static final List<String> SESSION_REGULAR = List.of(SESSION_REGULAR_VALUE);
  public static final List<String> SYMBOL = List.of(SYMBOL_VALUE);
  public static final List<String> TIMEZONE = List.of(TIMEZONE_VALUE);
  public static final List<String> TYPE = List.of(TYPE_VALUE);

  public static GetSymbolsResponsePayload create() {
    return GetSymbolsResponsePayload.builder()
      .baseCurrency(BASE_CURRENCY)
      .currency(CURRENCY)
      .description(DESCRIPTION)
      .exchangeListed(EXCHANGE_LISTED)
      .exchangeTraded(EXCHANGE_TRADED)
      .minMovement(MIN_MOVEMENT)
      .priceScale(PRICE_SCALE)
      .sessionRegular(SESSION_REGULAR)
      .symbol(SYMBOL)
      .timezone(TIMEZONE)
      .type(TYPE)
      .build();
  }
}
