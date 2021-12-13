package com.github.marceloleite2604.cryptotrader.model.trades;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TradesRequest {

  private final String symbol;

  private final Long tid;

  private final Long since;

  private final OffsetDateTime from;

  private final OffsetDateTime to;
}
