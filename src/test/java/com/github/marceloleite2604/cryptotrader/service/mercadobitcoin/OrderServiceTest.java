package com.github.marceloleite2604.cryptotrader.service.mercadobitcoin;

import com.github.marceloleite2604.cryptotrader.dto.orders.OrderDto;
import com.github.marceloleite2604.cryptotrader.mapper.OrderDtoMapper;
import com.github.marceloleite2604.cryptotrader.mapper.PlaceOrderRequestToPostOrderRequestPayloadMapper;
import com.github.marceloleite2604.cryptotrader.model.orders.RetrieveOrdersRequest;
import com.github.marceloleite2604.cryptotrader.test.util.MockWebServerUtil;
import com.github.marceloleite2604.cryptotrader.test.util.MockedWebClientTests;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.ErrorMessageDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.AccountDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.GetSymbolsResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.OrderDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.PostOrderRequestPayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.PostOrderResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orders.OrderFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orders.PlaceOrderRequestFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orders.RetrieveOrdersRequestFixture;
import com.github.marceloleite2604.cryptotrader.util.ValidationUtil;
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
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest extends MockedWebClientTests {

  private static final String SYMBOL = "symbolValue";

  @InjectMocks
  private OrderService orderService;

  @Mock
  private ValidationUtil validationUtil;

  @Mock
  private OrderDtoMapper orderDtoMapper;

  @Mock
  private PlaceOrderRequestToPostOrderRequestPayloadMapper placeOrderRequestToPostOrderRequestPayloadMapper;

  @Test
  @SuppressWarnings({"unchecked"})
  void shouldReturnOrdersList() {
    final var orderDtos = List.of(OrderDtoFixture.create());
    final var expected = List.of(OrderFixture.create());

    final var retrieveOrdersRequest = RetrieveOrdersRequestFixture.create();
    final var uri = buildGetOrdersUri(retrieveOrdersRequest);

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, orderDtos);

    final ArgumentCaptor<List<OrderDto>> orderDtosArgumentCaptor = ArgumentCaptor.forClass(List.class);

    when(orderDtoMapper.mapAllTo(orderDtosArgumentCaptor.capture())).thenReturn(expected);

    final var actual = orderService.retrieve(retrieveOrdersRequest);

    assertThat(actual).usingDefaultElementComparator()
      .usingRecursiveComparison()
      .isEqualTo(expected);

    assertThat(orderDtosArgumentCaptor.getValue())
      .usingRecursiveComparison()
      .isEqualTo(orderDtos);
  }

  @Test
  void shouldThrowIllegalStateExceptionWhenRetrieveOrdersDoesNotReturnContent() {
    final var retrieveOrdersRequest = RetrieveOrdersRequestFixture.create();
    final var uri = buildGetOrdersUri(retrieveOrdersRequest);

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, null);

    assertThrows(IllegalStateException.class, () -> orderService.retrieve(retrieveOrdersRequest));
  }

  private URI buildGetOrdersUri(RetrieveOrdersRequest retrieveOrdersRequest) {
    final var uriBuilder = new URIBuilder().setPathSegments(
      OrderService.ACCOUNTS,
      AccountDtoFixture.ID,
      SYMBOL, OrderService.ORDERS);

    if (retrieveOrdersRequest.getHasExecutions() != null) {
      uriBuilder.addParameter("has_executions", retrieveOrdersRequest.getHasExecutions()
        .toString());
    }

    if (retrieveOrdersRequest.getType() != null) {
      uriBuilder.addParameter("type", retrieveOrdersRequest.getType());
    }

    if (retrieveOrdersRequest.getStatus() != null) {
      uriBuilder.addParameter("status", retrieveOrdersRequest.getStatus());
    }

    if (retrieveOrdersRequest.getIdFrom() != null) {
      uriBuilder.addParameter("id_from", retrieveOrdersRequest.getIdFrom());
    }

    if (retrieveOrdersRequest.getIdTo() != null) {
      uriBuilder.addParameter("id_to", retrieveOrdersRequest.getIdTo());
    }

    if (retrieveOrdersRequest.getCreatedAtFrom() != null) {
      uriBuilder.addParameter("created_at_from", Long.toString(retrieveOrdersRequest.getCreatedAtFrom()
        .toEpochSecond()));
    }

    if (retrieveOrdersRequest.getCreatedAtTo() != null) {
      uriBuilder.addParameter("created_at_to", Long.toString(retrieveOrdersRequest.getCreatedAtTo()
        .toEpochSecond()));
    }

    try {
      return uriBuilder.build();
    } catch (URISyntaxException exception) {
      throw new IllegalStateException("Exception thrown while building list orders URI.", exception);
    }
  }

  @Test
  void shouldReturnOrder() throws Exception {
    final var orderDto = OrderDtoFixture.create();
    final var expected = OrderFixture.create();

    final var uri = buildGetOrderUri();

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, orderDto);

    final var orderDtoArgumentCaptor = ArgumentCaptor.forClass(OrderDto.class);

    when(orderDtoMapper.mapTo(orderDtoArgumentCaptor.capture())).thenReturn(expected);

    final var actual = orderService.retrieveOrder(AccountDtoFixture.ID, GetSymbolsResponsePayloadFixture.SYMBOL_VALUE, OrderDtoFixture.ID);

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);

    assertThat(orderDtoArgumentCaptor.getValue())
      .usingRecursiveComparison()
      .isEqualTo(orderDto);
  }

  @Test
  void shouldThrowIllegalStateExceptionWhenRetrieveOrderDoesNotReturnContent() throws Exception {
    final var uri = buildGetOrderUri();

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, null);

    assertThrows(IllegalStateException.class, () -> orderService.retrieveOrder(AccountDtoFixture.ID, GetSymbolsResponsePayloadFixture.SYMBOL_VALUE, OrderDtoFixture.ID));
  }

  private String buildGetOrderUri() throws Exception {
    return new URIBuilder().setPathSegments(
        OrderService.ACCOUNTS,
        AccountDtoFixture.ID,
        GetSymbolsResponsePayloadFixture.SYMBOL_VALUE,
        OrderService.ORDERS,
        OrderDtoFixture.ID)
      .build()
      .toASCIIString();
  }

  @Test
  void shouldPlaceOrderAndReturnOptionalWithId() throws Exception {
    final var expected = Optional.of(OrderDtoFixture.ID);
    final var placeOrderRequest = PlaceOrderRequestFixture.create();
    final var postOrderResponsePayload = PostOrderResponsePayloadFixture.create();

    final var uri = buildPostUri();

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.POST, uri)
      .thenReturn(HttpStatus.OK, postOrderResponsePayload);

    when(placeOrderRequestToPostOrderRequestPayloadMapper.mapTo(placeOrderRequest))
      .thenReturn(PostOrderRequestPayloadFixture.create());

    final var actual = orderService.placeOrder(placeOrderRequest);

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void shouldReturnEmptyOptionalWhenOrderHasNotBeenPlaced() throws Exception {
    final var expected = Optional.empty();
    final var placeOrderRequest = PlaceOrderRequestFixture.create();

    final var uri = buildPostUri();

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.POST, uri)
      .thenReturn(HttpStatus.BAD_REQUEST, ErrorMessageDtoFixture.create());

    when(placeOrderRequestToPostOrderRequestPayloadMapper.mapTo(placeOrderRequest))
      .thenReturn(PostOrderRequestPayloadFixture.create());

    final var actual = orderService.placeOrder(placeOrderRequest);

    assertThat(actual).isEqualTo(expected);
  }

  private String buildPostUri() throws Exception {
    return new URIBuilder().setPathSegments(
        OrderService.ACCOUNTS,
        AccountDtoFixture.ID,
        GetSymbolsResponsePayloadFixture.SYMBOL_VALUE,
        OrderService.ORDERS)
      .build()
      .toASCIIString();
  }

}