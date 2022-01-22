package com.github.marceloleite2604.cryptotrader.test.util.fixture.model;

import com.github.marceloleite2604.cryptotrader.model.Active;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ActiveFixture {

  public static final String BASE = "baseValue";
  public static final String QUOTE = "quoteName";
  public static final String NAME = "nameValue";

  public static Active retrieve() {
    if (Active.findByBase(BASE) == Active.UNKNOWN) {
      createAndAddActive();
    }

    return Active.findByBase(BASE);
  }

  private static void createAndAddActive() {
    final var active = Active.builder()
      .base(BASE)
      .quote(QUOTE)
      .name(NAME)
      .build();

    Active.add(active);
  }
}
