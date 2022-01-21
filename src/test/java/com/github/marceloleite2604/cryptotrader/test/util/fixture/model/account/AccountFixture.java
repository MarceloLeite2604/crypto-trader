package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.account;

import com.github.marceloleite2604.cryptotrader.model.account.Account;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.AccountDtoFixture;
import lombok.experimental.UtilityClass;

import java.util.Collections;

@UtilityClass
public class AccountFixture {

  public static Account create() {
    return Account.builder()
      .id(AccountDtoFixture.ID)
      .type(AccountDtoFixture.TYPE)
      .name(AccountDtoFixture.NAME)
      .currencySign(AccountDtoFixture.CURRENCY_SIGN)
      .currency(AccountDtoFixture.CURRENCY)
      .orders(Collections.emptyMap())
      .balances(Collections.emptyMap())
      .build();
  }
}
