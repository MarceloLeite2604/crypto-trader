package com.github.marceloleite2604.cryptotrader.model.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Account {

  private final String currency;

  private final String currencySign;

  private final String id;

  private final String name;

  private final String type;
}
