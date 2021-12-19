package com.github.marceloleite2604.cryptotrader.model.profit;

import com.github.marceloleite2604.cryptotrader.model.Symbol;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class Profit {

  private BigDecimal fiatCurrencyBalance = BigDecimal.ZERO;

  private BigDecimal cryptoCurrencyBalance = BigDecimal.ZERO;

  private BigDecimal value;

  private BigDecimal percentage;

  private Symbol symbol;

  public void addFiat(BigDecimal value) {
    fiatCurrencyBalance = fiatCurrencyBalance.add(value);
  }

  public void subtractFiat(BigDecimal value) {
    fiatCurrencyBalance = fiatCurrencyBalance.subtract(value);
  }

  public void addCrypto(BigDecimal value) {
    cryptoCurrencyBalance = cryptoCurrencyBalance.add(value);
  }

  public void subtractCrypto(BigDecimal value) {
    cryptoCurrencyBalance = cryptoCurrencyBalance.subtract(value);
  }
}
