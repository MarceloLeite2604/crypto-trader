package com.github.marceloleite2604.cryptotrader.service;

import com.github.marceloleite2604.cryptotrader.model.orders.OrdersRequest;
import com.github.marceloleite2604.cryptotrader.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.net.URISyntaxException;

@Component
@RequiredArgsConstructor
public class OrderService {

  private final ValidationUtil validationUtil;

  public void retrieve(OrdersRequest ordersRequest) {
    Assert.notNull(ordersRequest, "Must inform a valid orders request");
    validationUtil.throwIllegalArgumentExceptionIfNotValid(ordersRequest, "Orders request informed is invalid.");

    final var getOrdersUri = buildGetOrdersUri(ordersRequest);
  }

  private String buildGetOrdersUri(OrdersRequest ordersRequest) {
    final var uriBuilder = new URIBuilder().setPathSegments("accounts", ordersRequest.getAccountId(), ordersRequest.getSymbol(), "orders");

    if (ordersRequest.getHasExecutions() != null) {
      uriBuilder.addParameter("has_executions", ordersRequest.getHasExecutions()
        .toString());
    }

    if (ordersRequest.getType() != null) {
      uriBuilder.addParameter("type", ordersRequest.getType());
    }

    if (ordersRequest.getStatus() != null) {
      uriBuilder.addParameter("status", ordersRequest.getStatus());
    }

    if (ordersRequest.getIdFrom() != null) {
      uriBuilder.addParameter("id_from", ordersRequest.getIdFrom());
    }

    if (ordersRequest.getIdTo() != null) {
      uriBuilder.addParameter("id_to", ordersRequest.getIdTo());
    }

    if (ordersRequest.getCreatedAtFrom() != null) {
      uriBuilder.addParameter("created_at_from", Long.toString(ordersRequest.getCreatedAtFrom()
        .toEpochSecond()));
    }

    if (ordersRequest.getCreatedAtTo() != null) {
      uriBuilder.addParameter("created_at_to", Long.toString(ordersRequest.getCreatedAtTo()
        .toEpochSecond()));
    }

    try {
      return uriBuilder
        .build()
        .toString();
    } catch (URISyntaxException exception) {
      throw new IllegalStateException("Exception thrown while building orders URI.", exception);
    }
  }
}
