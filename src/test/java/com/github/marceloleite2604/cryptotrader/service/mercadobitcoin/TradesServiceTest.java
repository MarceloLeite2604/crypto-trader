package com.github.marceloleite2604.cryptotrader.service.mercadobitcoin;

import com.github.marceloleite2604.cryptotrader.dto.trades.TradeDto;
import com.github.marceloleite2604.cryptotrader.mapper.TradeDtoMapper;
import com.github.marceloleite2604.cryptotrader.model.trades.TradesRequest;
import com.github.marceloleite2604.cryptotrader.test.util.MockWebServerUtil;
import com.github.marceloleite2604.cryptotrader.test.util.MockedWebClientTests;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.trades.TradeDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.trades.TradeFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.trades.TradesRequestFixture;
import com.github.marceloleite2604.cryptotrader.util.ValidationUtil;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TradesServiceTest extends MockedWebClientTests {

  @InjectMocks
  private TradesService tradesService;

  @Mock
  private TradeDtoMapper tradeDtoMapper;

  @Mock
  private ValidationUtil validationUtil;

  @Test
  @SuppressWarnings({"unchecked"})
  void shouldReturnTrades() {
    final var tradeDtos = List.of(TradeDtoFixture.create());
    final var expected = List.of(TradeFixture.create());
    final var tradesRequest = TradesRequestFixture.create();

    final var uri = buildUri(tradesRequest);

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, tradeDtos);

    final ArgumentCaptor<List<TradeDto>> tradeDtosArgumentCaptor = ArgumentCaptor.forClass(List.class);

    when(tradeDtoMapper.mapAllTo(tradeDtosArgumentCaptor.capture())).thenReturn(expected);

    final var actual = tradesService.retrieve(tradesRequest);

    assertThat(actual).usingDefaultElementComparator()
      .usingRecursiveComparison()
      .isEqualTo(expected);

    assertThat(tradeDtosArgumentCaptor.getValue()).usingDefaultElementComparator()
      .usingRecursiveComparison()
      .isEqualTo(tradeDtos);
  }

  @Test
  void shouldThrowIllegalStateExceptionWhenNoDataIsReceived() {
    final var tradesRequest = TradesRequestFixture.create();

    final var uri = buildUri(tradesRequest);

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, null);

    assertThrows(IllegalStateException.class, () -> tradesService.retrieve(tradesRequest));
  }

  @SneakyThrows
  private String buildUri(TradesRequest tradesRequest) {

    final var uriBuilder = new URIBuilder().setPathSegments(tradesRequest.getSymbol(), "trades");

    if (tradesRequest.getTid() != null) {
      uriBuilder.addParameter("tid", Long.toString(tradesRequest.getTid()));
    }

    if (tradesRequest.getSince() != null) {
      uriBuilder.addParameter("since", Long.toString(tradesRequest.getSince()));
    }

    if (tradesRequest.getFrom() != null) {
      uriBuilder.addParameter("from", Long.toString(tradesRequest.getFrom()
        .toEpochSecond()));
    }

    if (tradesRequest.getTo() != null) {
      uriBuilder.addParameter("to", Long.toString(tradesRequest.getTo()
        .toEpochSecond() - 1));
    }

    return uriBuilder
      .build()
      .toASCIIString();
  }
}