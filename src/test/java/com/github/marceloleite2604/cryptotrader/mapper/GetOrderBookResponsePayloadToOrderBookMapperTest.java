package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orderbook.GetOrderBookResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orderbook.OrderBookFixture;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetOrderBookResponsePayloadToOrderBookMapperTest {

  @InjectMocks
  private GetOrderBookResponsePayloadToOrderBookMapper getOrderBookResponsePayloadToOrderBookMapper;

  @Mock
  private DateTimeUtil dateTimeUtil;

  @Test
  void shouldReturnOrderBook() {
    final var expected = OrderBookFixture.create();
    when(dateTimeUtil.convertTimestampWithNanosToUtcOffsetDateTime(anyLong())).thenReturn(OrderBookFixture.TIMESTAMP);

    final var actual = getOrderBookResponsePayloadToOrderBookMapper.mapTo(GetOrderBookResponsePayloadFixture.create());

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

  @Test
  void shouldReturnOrderBookWithEmptyOrderItemsListWhenNoOrdersExists() {
    final var expected = OrderBookFixture.createWithEmptyOrderBookItems();
    when(dateTimeUtil.convertTimestampWithNanosToUtcOffsetDateTime(anyLong())).thenReturn(OrderBookFixture.TIMESTAMP);

    final var actual = getOrderBookResponsePayloadToOrderBookMapper.mapTo(GetOrderBookResponsePayloadFixture.createWithNoAsksAndBids());

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }
}