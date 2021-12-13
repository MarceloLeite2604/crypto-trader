package com.github.marceloleite2604.cryptotrader.dto.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class AccountDto {

  private final String currency;

  private final String currencySign;

  private final String id;

  private final String name;

  private final String type;
}
