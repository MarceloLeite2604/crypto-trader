package com.github.marceloleite2604.cryptotrader.service;

import com.github.marceloleite2604.cryptotrader.configuration.GeneralConfiguration;
import com.github.marceloleite2604.cryptotrader.model.Profit;
import com.github.marceloleite2604.cryptotrader.model.Side;
import com.github.marceloleite2604.cryptotrader.model.Symbol;
import com.github.marceloleite2604.cryptotrader.model.Ticker;
import com.github.marceloleite2604.cryptotrader.model.orders.Execution;
import com.github.marceloleite2604.cryptotrader.model.orders.Order;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProfitCalculatorService {

  private final BigDecimal MAKER_FEE_PERCENTAGE = BigDecimal.valueOf(0.003);
  private final BigDecimal TAKER_FEE_PERCENTAGE = BigDecimal.valueOf(0.007);

  public Profit calculate(List<Order> orders, Ticker ticker) {

    final var profit = new Profit();

    if (CollectionUtils.isEmpty(orders)) {
      return profit;
    }

    final var symbol = Symbol.findByValue(orders.get(0).getInstrument());

    profit.setSymbol(symbol);

    for (Order order : orders) {
      for (Execution execution : order.getExecutions()) {
        final var feePercentage = order.getSide()
          .equals(execution.getSide()) ? TAKER_FEE_PERCENTAGE : MAKER_FEE_PERCENTAGE;

        if ("sell".equals(order.getSide())) {
          final var netIncome = execution.getPrice()
            .multiply(execution.getQuantity())
            .multiply(BigDecimal.ONE.subtract(feePercentage));

          profit.subtractCrypto(execution.getQuantity());
          profit.addFiat(netIncome);
        } else {
          final var netIncome = execution.getQuantity()
            .multiply(BigDecimal.ONE.subtract(feePercentage));
          final var cost = execution.getPrice()
            .multiply(execution.getQuantity());
          profit.subtractFiat(cost);
          profit.addCrypto(netIncome);
        }
      }
    }

    final var currentCryptoBalanceAsFiat = profit.getCryptoCurrencyBalance()
      .multiply(ticker.getLast());

    final var value = currentCryptoBalanceAsFiat.add(profit.getFiatCurrencyBalance());

    final var percentage = value.divide(profit.getFiatCurrencyBalance()
      .negate(), GeneralConfiguration.DEFAULT_ROUNDING_MODE);

    profit.setValue(value);
    profit.setPercentage(percentage);

    return profit;
  }
}
