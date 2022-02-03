package com.github.marceloleite2604.cryptotrader.service.mercadobitcoin;

import com.github.marceloleite2604.cryptotrader.dto.tickers.TickerDto;
import com.github.marceloleite2604.cryptotrader.mapper.ListTickersDtoToMapTickersMapper;
import com.github.marceloleite2604.cryptotrader.test.util.MockWebServerUtil;
import com.github.marceloleite2604.cryptotrader.test.util.MockedWebClientTests;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.GetSymbolsResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.tickers.TickerDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.TickerFixture;
import lombok.SneakyThrows;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TickerServiceTest extends MockedWebClientTests {

  @InjectMocks
  private TickersService tickersService;

  @Mock
  private ListTickersDtoToMapTickersMapper listTickersDtoToMapTickersMapper;

  @Test
  @SuppressWarnings("unchecked")
  void shouldReturnTickers() {

    final var tickerDtos = List.of(TickerDtoFixture.create());

    final var expected = Map.of(GetSymbolsResponsePayloadFixture.SYMBOL_VALUE, TickerFixture.create());

    final var uri = buildTickersUri();

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, tickerDtos);

    final ArgumentCaptor<List<TickerDto>> tickerDtosArgumentCaptor = ArgumentCaptor.forClass(List.class);

    when(listTickersDtoToMapTickersMapper.mapTo(tickerDtosArgumentCaptor.capture()))
      .thenReturn(expected);

    final var actual = tickersService.retrieve(GetSymbolsResponsePayloadFixture.SYMBOL_VALUE);

    assertThat(actual)
      .usingRecursiveComparison()
      .isEqualTo(expected);

    assertThat(tickerDtosArgumentCaptor.getValue()).usingDefaultElementComparator()
      .usingRecursiveComparison()
      .isEqualTo(tickerDtos);
  }

  @Test
  void shouldThrowIllegalStateExceptionWhenNoTickerIsReturned() {

    final var uri = buildTickersUri();

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, null);

    assertThrows(IllegalStateException.class, () -> tickersService.retrieve(GetSymbolsResponsePayloadFixture.SYMBOL_VALUE));
  }

  @Test
  void shouldThrowIllegalArgumentExceptionIfNoSymbolIsProvided() {
    assertThrows(IllegalArgumentException.class, () -> tickersService.retrieve());
  }

  @SneakyThrows
  private String buildTickersUri() {
    final var queryParameterSymbolsNameValuePair = Stream.of(GetSymbolsResponsePayloadFixture.SYMBOL_VALUE)
      .map(symbol -> (NameValuePair) new BasicNameValuePair("symbols", symbol))
      .toList();

    return new URIBuilder().setPathSegments("tickers")
      .addParameters(queryParameterSymbolsNameValuePair)
      .build()
      .toString();
  }

}