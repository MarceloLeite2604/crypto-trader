package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.trades.TradeDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.trades.TradeFixture;
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
class TradeDtoMapperTest {

  @InjectMocks
  private TradeDtoMapper tradeDtoMapper;

  @Mock
  private DateTimeUtil dateTimeUtil;

  @Test
  void shouldReturnTrade() {
    final var expected = TradeFixture.create();
    when(dateTimeUtil.convertEpochToUtcOffsetDateTime(anyLong())).thenReturn(TradeFixture.DATE);

    final var actual = tradeDtoMapper.mapTo(TradeDtoFixture.create());


    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

}