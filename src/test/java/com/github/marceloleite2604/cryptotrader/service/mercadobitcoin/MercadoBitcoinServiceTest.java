package com.github.marceloleite2604.cryptotrader.service.mercadobitcoin;

import com.github.marceloleite2604.cryptotrader.model.account.Balance;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.AccountDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.OrderDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.ActiveFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.InstrumentFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.TickerFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.account.AccountFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.account.BalanceFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.account.PositionFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.candles.CandleFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.candles.CandlesRequestFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orderbook.OrderBookFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orders.OrderFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orders.PlaceOrderRequestFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orders.RetrieveOrdersRequestFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.trades.TradeFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.trades.TradesRequestFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MercadoBitcoinServiceTest {

  @InjectMocks
  private MercadoBitcoinService mercadoBitcoinService;

  @Mock
  private InstrumentService instrumentService;

  @Mock
  private OrderBookService orderBookService;

  @Mock
  private TickersService tickersService;

  @Mock
  private CandlesService candlesService;

  @Mock
  private TradesService tradesService;

  @Mock
  private AccountsService accountsService;

  @Mock
  private BalanceService balanceService;

  @Mock
  private PositionService positionService;

  @Mock
  private OrderService orderService;

  @Test
  void shouldRetrieveInstruments() {
    final var expected = Set.of(InstrumentFixture.create());

    when(instrumentService.retrieveAll()).thenReturn(expected);

    final var actual = mercadoBitcoinService.retrieveAllInstruments();

    assertThat(actual).usingRecursiveFieldByFieldElementComparator()
      .isEqualTo(expected);
  }

  @Test
  void shouldRetrieveOrderBook() {
    final var expected = OrderBookFixture.create();
    final var active = ActiveFixture.retrieve();

    when(orderBookService.retrieve(active.getSymbol(), null)).thenReturn(expected);

    final var actual = mercadoBitcoinService.retrieveOrderBook(active);

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

  @Test
  void shouldRetrieveTicker() {
    final var expected = TickerFixture.create();
    final var active = ActiveFixture.retrieve();
    final var tickersMap = Map.of(active.getSymbol(), expected);

    when(tickersService.retrieve(active.getSymbol())).thenReturn(tickersMap);

    final var actual = mercadoBitcoinService.retrieveTicker(active);

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

  @Test
  void shouldRetrieveCandles() {
    final var candlesRequest = CandlesRequestFixture.createWithTime();
    final var expected = List.of(CandleFixture.createRaw());

    when(candlesService.retrieve(candlesRequest)).thenReturn(expected);

    final var actual = mercadoBitcoinService.retrieveCandles(candlesRequest);

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

  @Test
  void shouldRetrieveTrades() {
    final var tradesRequest = TradesRequestFixture.create();
    final var expected = List.of(TradeFixture.create());

    when(tradesService.retrieve(tradesRequest)).thenReturn(expected);

    final var actual = mercadoBitcoinService.retrieveTrades(tradesRequest);

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

  @Test
  void shouldRetrieveAccount() {
    final var expected = AccountFixture.create();
    final var accounts = List.of(AccountFixture.create());

    when(accountsService.retrieve()).thenReturn(accounts);

    final var actual = mercadoBitcoinService.retrieveAccount();

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

  @Test
  void shouldRetrieveBalance() {

    final var active = ActiveFixture.retrieve();
    final var expected = BalanceFixture.create();
    final var balances = List.of(expected);
    when(balanceService.retrieve(AccountDtoFixture.ID, active.getSymbol())).thenReturn(balances);

    final var actual = mercadoBitcoinService.retrieveBalance(AccountDtoFixture.ID, active);

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

  @Test
  void shouldThrowIllegalStateExceptionWhenBalanceIsNotFound() {

    final var active = ActiveFixture.retrieve();
    final List<Balance> balances = Collections.emptyList();
    when(balanceService.retrieve(AccountDtoFixture.ID, active.getSymbol())).thenReturn(balances);

    assertThrows(IllegalStateException.class, () -> mercadoBitcoinService.retrieveBalance(AccountDtoFixture.ID, active));
  }

  @Test
  void shouldRetrievePositions() {

    final var active = ActiveFixture.retrieve();
    final var expected = List.of(PositionFixture.create());

    when(positionService.retrieve(AccountDtoFixture.ID, active.getSymbol())).thenReturn(expected);

    final var actual = mercadoBitcoinService.retrievePositions(AccountDtoFixture.ID, active);

    assertThat(actual).usingRecursiveFieldByFieldElementComparator()
      .isEqualTo(expected);
  }

  @Test
  void shouldRetrieveOrders() {

    final var expected = List.of(OrderFixture.create());
    final var retrieveOrdersRequest = RetrieveOrdersRequestFixture.create();

    when(orderService.retrieve(retrieveOrdersRequest)).thenReturn(expected);

    final var actual = mercadoBitcoinService.retrieveOrders(retrieveOrdersRequest);

    assertThat(actual).usingRecursiveFieldByFieldElementComparator()
      .isEqualTo(expected);
  }

  @Test
  void shouldRetrieveOrder() {

    final var expected = OrderFixture.create();
    final var accountId = AccountDtoFixture.ID;
    final var active = ActiveFixture.retrieve();
    final var orderId = OrderDtoFixture.ID;

    when(orderService.retrieveOrder(accountId, active.getSymbol(), orderId)).thenReturn(expected);

    final var actual = mercadoBitcoinService.retrieveOrder(accountId, active, orderId);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldPlaceOrder() {

    final var expected = Optional.of(OrderDtoFixture.ID);
    final var placeOrderRequest = PlaceOrderRequestFixture.create();

    when(orderService.placeOrder(placeOrderRequest)).thenReturn(expected);

    final var actual = mercadoBitcoinService.placeOrder(placeOrderRequest);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldCancelOrder() {

    final var expected = true;
    final var active = ActiveFixture.retrieve();
    final var accountId = AccountDtoFixture.ID;
    final var orderId = OrderDtoFixture.ID;

    when(orderService.cancelOrder(accountId, active.getSymbol(), orderId)).thenReturn(true);

    final var actual = mercadoBitcoinService.cancelOrder(accountId, active, orderId);

    assertThat(actual).isEqualTo(expected);
  }

}