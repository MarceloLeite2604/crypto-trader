package com.github.marceloleite2604.cryptotrader.dto.account;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Getter
public class BalanceDto {

  private double available;

  private String symbol;

  private double total;
}
