package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.OrderDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.ExecutionFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orders.OrderFixture;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderDtoMapperTest {

  @InjectMocks
  private OrderDtoMapper orderDtoMapper;

  @Mock
  private DateTimeUtil dateTimeUtil;

  @Mock
  private ExecutionDtoMapper executionDtoMapper;

  @Test
  void shouldReturnOrder() {
    final var expected = OrderFixture.create();

    when(executionDtoMapper.mapAllTo(any())).thenReturn(List.of(ExecutionFixture.create()));
    when(dateTimeUtil.convertEpochToUtcOffsetDateTime(OrderDtoFixture.CREATED_AT)).thenReturn(OrderFixture.CREATED_AT);
    when(dateTimeUtil.convertEpochToUtcOffsetDateTime(OrderDtoFixture.UPDATED_AT)).thenReturn(OrderFixture.UPDATED_AT);

    final var actual = orderDtoMapper.mapTo(OrderDtoFixture.create());

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

}