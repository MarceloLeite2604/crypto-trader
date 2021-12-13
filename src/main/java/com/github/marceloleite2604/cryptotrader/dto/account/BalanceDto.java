package com.github.marceloleite2604.cryptotrader.dto.account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class BalanceDto {

  private double available;

  private String symbol;

  private double total;
}
