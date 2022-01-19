package com.github.marceloleite2604.cryptotrader.test.util.fixture.account;

import com.github.marceloleite2604.cryptotrader.dto.account.AccountDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AccountDtoFixture {

  public static final String ID = "idValue";
  public static final String CURRENCY = "currencyValue";
  public static final String CURRENCY_SIGN = "currencySignValue";
  public static final String NAME = "nameValue";
  public static final String TYPE = "typeValue";

  public static AccountDto create() {
    return AccountDto.builder()
      .id(ID)
      .currency(CURRENCY)
      .currencySign(CURRENCY_SIGN)
      .name(NAME)
      .type(TYPE)
      .build();
  }
}
