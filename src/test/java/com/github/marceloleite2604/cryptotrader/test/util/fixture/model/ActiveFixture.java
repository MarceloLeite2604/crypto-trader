package com.github.marceloleite2604.cryptotrader.test.util.fixture.model;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.GetSymbolsResponsePayloadFixture;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ActiveFixture {

  public static final String BASE = GetSymbolsResponsePayloadFixture.BASE_CURRENCY_VALUE;

  public static Active retrieve() {
    if (Active.findByBase(BASE) == Active.UNKNOWN) {
      createAndAddActive();
    }

    return Active.findByBase(BASE);
  }

  private static void createAndAddActive() {
    final var active = Active.builder()
      .base(BASE)
      .quote(GetSymbolsResponsePayloadFixture.CURRENCY_VALUE)
      .name(GetSymbolsResponsePayloadFixture.DESCRIPTION_VALUE)
      .build();

    Active.add(active);
  }
}
