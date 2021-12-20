package com.github.marceloleite2604.cryptotrader.service;

import com.github.marceloleite2604.cryptotrader.configuration.GeneralConfiguration;
import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.orders.Execution;
import com.github.marceloleite2604.cryptotrader.model.orders.Order;
import com.github.marceloleite2604.cryptotrader.model.orders.RetrieveOrdersRequest;
import com.github.marceloleite2604.cryptotrader.model.profit.Profit;
import com.github.marceloleite2604.cryptotrader.model.profit.ProfitId;
import com.github.marceloleite2604.cryptotrader.repository.ProfitRepository;
import com.github.marceloleite2604.cryptotrader.service.mercadobitcoin.MercadoBitcoinService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfitService {

  private static final BigDecimal PROFIT_THRESHOLD_STEP = BigDecimal.valueOf(0.005);
  private static final BigDecimal MAKER_FEE_PERCENTAGE = BigDecimal.valueOf(0.003);
  private static final BigDecimal TAKER_FEE_PERCENTAGE = BigDecimal.valueOf(0.007);

  private final MercadoBitcoinService mercadoBitcoinService;
  private final ProfitRepository profitRepository;

  public Profit retrieve(String accountId, Active active) {

    final var profit = retrieveFromDatabase(accountId, active).orElseGet(() -> createDefault(accountId, active));

    final var current = calculateCurrent(accountId, active);

    return profit.toBuilder()
      .current(current)
      .build();
  }

  private BigDecimal calculateCurrent(String accountId, Active active) {
    final var ticker = mercadoBitcoinService.retrieveTicker(active.getSymbol());

    final var orders = retrieveOrders(accountId, active);

    if (CollectionUtils.isEmpty(orders)) {
      return BigDecimal.ZERO;
    }

    var cryptoBalance = BigDecimal.ZERO;
    var fiatBalance = BigDecimal.ZERO;

    for (Order order : orders) {
      for (Execution execution : order.getExecutions()) {
        final var feePercentage = order.getSide()
          .equals(execution.getSide()) ? TAKER_FEE_PERCENTAGE : MAKER_FEE_PERCENTAGE;

        if ("sell".equals(order.getSide())) {
          final var netIncome = execution.getPrice()
            .multiply(execution.getQuantity())
            .multiply(BigDecimal.ONE.subtract(feePercentage));

          cryptoBalance = cryptoBalance.subtract(execution.getQuantity());
          fiatBalance = fiatBalance.add(netIncome);
        } else {
          final var netIncome = execution.getQuantity()
            .multiply(BigDecimal.ONE.subtract(feePercentage));
          final var cost = execution.getPrice()
            .multiply(execution.getQuantity());
          fiatBalance = fiatBalance.subtract(cost);
          cryptoBalance = cryptoBalance.add(netIncome);
        }
      }
    }

    final var cryptoBalanceAsFiat = cryptoBalance.multiply(ticker.getLast());

    final var value = cryptoBalanceAsFiat.add(fiatBalance);

    return value.divide(fiatBalance.negate(), GeneralConfiguration.DEFAULT_ROUNDING_MODE);
  }

  private Optional<Profit> retrieveFromDatabase(String accountId, Active active) {
    return retrieveFromDatabase(ProfitId.builder()
      .accountId(accountId)
      .active(active)
      .build());
  }

  private Optional<Profit> retrieveFromDatabase(ProfitId profitId) {
    return profitRepository.findById(profitId);
  }

  private Profit createDefault(String accountId, Active active) {

    final var id = ProfitId.builder()
      .accountId(accountId)
      .active(active)
      .build();

    return Profit.builder()
      .id(id)
      .current(BigDecimal.ZERO)
      .lower(PROFIT_THRESHOLD_STEP.negate())
      .upper(PROFIT_THRESHOLD_STEP)
      .build();
  }

  private List<Order> retrieveOrders(String accountId, Active active) {
    final var retrieveOrdersRequest = RetrieveOrdersRequest.builder()
      .accountId(accountId)
      .symbol(active.getSymbol())
      .build();
    return mercadoBitcoinService.retrieveOrders(retrieveOrdersRequest);
  }

  public Profit updateAndSave(Profit profit) {
    var profitMarginToPersist = profit;

    if (profit.hasReachedUpperLimit() || profit.hasReachedLowerLimit()) {
      final var current = profit.getCurrent();
      final var upper = current.add(PROFIT_THRESHOLD_STEP);
      final var lower = current.subtract(PROFIT_THRESHOLD_STEP);

      profitMarginToPersist = profit.toBuilder()
        .upper(upper)
        .lower(lower)
        .build();
    }

    return profitRepository.save(profitMarginToPersist);
  }
}
