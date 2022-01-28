package com.github.marceloleite2604.cryptotrader.service.mercadobitcoin;

import com.github.marceloleite2604.cryptotrader.dto.candle.GetCandleResponsePayload;
import com.github.marceloleite2604.cryptotrader.mapper.GetCandleResponsePayloadToListCandleMapper;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.test.util.MockWebServerUtil;
import com.github.marceloleite2604.cryptotrader.test.util.MockedWebClientTests;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.candle.GetCandleResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.ActiveFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.candles.CandleFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.candles.CandlesRequestFixture;
import com.github.marceloleite2604.cryptotrader.util.ValidationUtil;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CandlesServiceTest extends MockedWebClientTests {

  @InjectMocks
  private CandlesService candlesService;

  @Mock
  private GetCandleResponsePayloadToListCandleMapper getCandleResponsePayloadToListCandleMapper;

  @Mock
  private ValidationUtil validationUtil;

  private static Stream<CandlesRequest> provideValuesForTest() {
    return Stream.of(CandlesRequestFixture.createWithCountback(),
      CandlesRequestFixture.createWithTime());
  }

  @ParameterizedTest
  @MethodSource("provideValuesForTest")
  void shouldReturnCandles(CandlesRequest candlesRequest) throws Exception {
    final var getCandleResponsePayload = GetCandleResponsePayloadFixture.create();
    final var expected = List.of(CandleFixture.createRaw());

    final var uri = buildUri(candlesRequest.getCountback() != null);

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, getCandleResponsePayload);

    final var getCandleResponsePayloadArgumentCaptor = ArgumentCaptor.forClass(GetCandleResponsePayload.class);

    when(getCandleResponsePayloadToListCandleMapper.mapTo(getCandleResponsePayloadArgumentCaptor.capture())).thenReturn(expected);

    final var actual = candlesService.retrieve(candlesRequest);

    assertThat(actual).usingDefaultElementComparator()
      .usingRecursiveComparison()
      .isEqualTo(expected);

    assertThat(getCandleResponsePayloadArgumentCaptor.getValue())
      .usingRecursiveComparison()
      .isEqualTo(getCandleResponsePayload);
  }

  @Test
  void shouldThrowIllegalStateExceptionWhenNoResponseIsReceived() throws Exception {
    final var uri = buildUri(false);

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, null);

    final var candlesRequest = CandlesRequestFixture.createWithTime();

    assertThrows(IllegalStateException.class, () -> candlesService.retrieve(candlesRequest));
  }

  private URI buildUri(boolean useCountback) throws URISyntaxException {
    final var uriBuilder = new URIBuilder().setPathSegments("candles")
      .addParameter("symbol", ActiveFixture.retrieve()
        .getSymbol())
      .addParameter("resolution", CandleFixture.PRECISION.getValue());


    if (useCountback) {
      uriBuilder.addParameter("to", Integer.toString(CandlesRequestFixture.TO_COUNT));
      uriBuilder.addParameter("countback", Integer.toString(CandlesRequestFixture.COUNTBACK));
    } else {
      uriBuilder.addParameter("to", Long.toString(CandlesRequestFixture.TO_TIME.toEpochSecond() - 1));
      uriBuilder.addParameter("from", Long.toString(CandlesRequestFixture.FROM.toEpochSecond()));
    }

    return uriBuilder.build();
  }
}