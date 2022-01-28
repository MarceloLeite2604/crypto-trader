package com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orders;

import com.github.marceloleite2604.cryptotrader.model.orders.RetrieveOrdersRequest;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.AccountDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.GetSymbolsResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.OrderDtoFixture;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class RetrieveOrdersRequestFixture {

  public static final boolean HAS_EXECUTIONS = true;

  public static final String ID_FROM = "idFromValue";

  public static final String ID_TO = "idToValue";

  public static final OffsetDateTime CREATED_AT_FROM = OffsetDateTime.of(
    LocalDateTime.of(2022, 1, 27, 22, 42, 47),
    ZoneOffset.UTC);

  public static final OffsetDateTime CREATED_AT_TO = OffsetDateTime.of(
    LocalDateTime.of(2022, 1, 27, 22, 43, 21),
    ZoneOffset.UTC);

  public RetrieveOrdersRequest create() {
    return RetrieveOrdersRequest.builder()
      .accountId(AccountDtoFixture.ID)
      .symbol(GetSymbolsResponsePayloadFixture.SYMBOL_VALUE)
      .hasExecutions(HAS_EXECUTIONS)
      .type(OrderDtoFixture.TYPE)
      .status(OrderDtoFixture.STATUS)
      .idFrom(ID_FROM)
      .idTo(ID_TO)
      .createdAtFrom(CREATED_AT_FROM)
      .createdAtTo(CREATED_AT_TO)
      .build();
  }
}
