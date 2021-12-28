package com.github.marceloleite2604.cryptotrader.service;

import com.github.marceloleite2604.cryptotrader.configuration.GeneralConfiguration;
import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.Side;
import com.github.marceloleite2604.cryptotrader.model.orders.Order;
import com.github.marceloleite2604.cryptotrader.model.orders.RetrieveOrdersRequest;
import com.github.marceloleite2604.cryptotrader.model.profit.Profit;
import com.github.marceloleite2604.cryptotrader.model.profit.ProfitId;
import com.github.marceloleite2604.cryptotrader.repository.ProfitRepository;
import com.github.marceloleite2604.cryptotrader.service.mercadobitcoin.MercadoBitcoinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfitService {

  private static final BigDecimal PROFIT_THRESHOLD_STEP = BigDecimal.valueOf(0.0142);

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

    return retrieveOrders(accountId, active).stream()
      .filter(order -> "filled".equals(order.getStatus()))
      .filter(order -> Side.BUY.name()
        .equalsIgnoreCase(order.getSide()))
      .max(Comparator.comparing(Order::getCreatedAt))
      .map(lastBuyOrder -> {
        final var lastBuyOrderPrice = lastBuyOrder.getAveragePrice();
        final var lastPrice = ticker.getLast();

        return lastPrice.subtract(lastBuyOrderPrice)
          .divide(lastBuyOrderPrice, GeneralConfiguration.DEFAULT_ROUNDING_MODE);
      }).orElse(BigDecimal.ZERO);
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
