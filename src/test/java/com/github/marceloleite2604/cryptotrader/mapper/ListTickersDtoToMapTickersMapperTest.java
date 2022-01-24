package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.tickers.TickerDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.TickerFixture;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListTickersDtoToMapTickersMapperTest {

  @InjectMocks
  private ListTickersDtoToMapTickersMapper listTickersDtoToMapTickersMapper;

  @Mock
  private DateTimeUtil dateTimeUtil;

  @Test
  void shouldReturnMapTickers() {
    final var expected = Map.of(TickerDtoFixture.PAIR, TickerFixture.create());
    when(dateTimeUtil.convertTimestampWithNanosToUtcOffsetDateTime(anyLong())).thenReturn(TickerFixture.DATE);

    final var actual = listTickersDtoToMapTickersMapper.mapTo(List.of(TickerDtoFixture.create()));

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

  @Test
  void shouldReturnEmptyMapWhenTickerDtoListIsEmpty() {
    final var expected = Collections.emptyMap();

    final var actual = listTickersDtoToMapTickersMapper.mapTo(Collections.emptyList());

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }
}