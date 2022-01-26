package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.dto.candle.GetCandleResponsePayload;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.candle.GetCandleResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.candles.CandleFixture;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetCandleResponsePayloadToListCandleMapperTest {

  @InjectMocks
  private GetCandleResponsePayloadToListCandleMapper getCandleResponsePayloadToListCandleMapper;

  @Mock
  private DateTimeUtil dateTimeUtil;

  @Test
  void shouldReturnCandlesList() {
    final var expected = List.of(CandleFixture.createRaw());

    when(dateTimeUtil.convertEpochToUtcOffsetDateTime(anyLong())).thenReturn(CandleFixture.TIMESTAMP);

    final var actual = getCandleResponsePayloadToListCandleMapper.mapTo(GetCandleResponsePayloadFixture.create());

    assertThat(actual).usingDefaultElementComparator()
      .usingRecursiveComparison()
      .ignoringFields("precision", "symbol")
      .isEqualTo(expected);
  }

  @Test
  void shouldReturnEmptyListWhenGetCandleResponsePayloadDoesNotHaveContent() {

    final var getCandleResponsePayload = GetCandleResponsePayload.builder()
      .build();

    final var actual = getCandleResponsePayloadToListCandleMapper.mapTo(getCandleResponsePayload);

    assertThat(actual).isEmpty();
  }
}