package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.ExecutionDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.ExecutionFixture;
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
class ExecutionDtoMapperTest {

  @InjectMocks
  private ExecutionDtoMapper executionDtoMapper;

  @Mock
  private DateTimeUtil dateTimeUtil;

  @Test
  void shouldReturnExecution() {
    final var expected = ExecutionFixture.create();

    when(dateTimeUtil.convertEpochToUtcOffsetDateTime(anyLong())).thenReturn(ExecutionFixture.EXECUTED_AT);

    final var actual = executionDtoMapper.mapTo(ExecutionDtoFixture.create());

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }


}