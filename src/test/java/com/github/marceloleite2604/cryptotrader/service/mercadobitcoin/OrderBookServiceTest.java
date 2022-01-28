package com.github.marceloleite2604.cryptotrader.service.mercadobitcoin;

import com.github.marceloleite2604.cryptotrader.dto.orderbook.GetOrderBookResponsePayload;
import com.github.marceloleite2604.cryptotrader.mapper.GetOrderBookResponsePayloadToOrderBookMapper;
import com.github.marceloleite2604.cryptotrader.test.util.MockWebServerUtil;
import com.github.marceloleite2604.cryptotrader.test.util.MockedWebClientTests;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orderbook.GetOrderBookResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orderbook.OrderBookFixture;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderBookServiceTest extends MockedWebClientTests {

  public static final String SYMBOL = "symbolValue";
  public static final Integer LIMIT = 24069738;
  @InjectMocks
  private OrderBookService orderBookService;

  @Mock
  private GetOrderBookResponsePayloadToOrderBookMapper getOrderBookResponsePayloadToOrderBookMapper;

  @Test
  void shouldReturnOrderBook() throws Exception {
    final var getOrderBookResponsePayload = GetOrderBookResponsePayloadFixture.create();
    final var expected = OrderBookFixture.create();

    final URI uri = buildUri();

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, getOrderBookResponsePayload);

    final var getOrderBookResponsePayloadArgumentCaptor = ArgumentCaptor.forClass(GetOrderBookResponsePayload.class);

    when(getOrderBookResponsePayloadToOrderBookMapper.mapTo(getOrderBookResponsePayloadArgumentCaptor.capture()))
      .thenReturn(expected);

    final var actual = orderBookService.retrieve(SYMBOL, LIMIT);

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);

    assertThat(getOrderBookResponsePayloadArgumentCaptor.getValue())
      .usingRecursiveComparison()
      .isEqualTo(getOrderBookResponsePayload);
  }

  @Test
  void shouldThrowIllegalStateExceptionWhenNoContentIsReturned() throws Exception {
    final URI uri = buildUri();

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, null);

    assertThrows(IllegalStateException.class, () -> orderBookService.retrieve(SYMBOL, LIMIT));

  }

  private URI buildUri() throws URISyntaxException {
    return new URIBuilder().setPathSegments(SYMBOL, "orderbook")
      .addParameter("limit", LIMIT.toString())
      .build();
  }
}