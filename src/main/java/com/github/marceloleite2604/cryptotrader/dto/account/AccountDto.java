package com.github.marceloleite2604.cryptotrader.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Getter
public class AccountDto {

  private final String currency;

  private final String currencySign;

  private final String id;

  private final String name;

  private final String type;
}
