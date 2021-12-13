package com.github.marceloleite2604.cryptotrader.model.trades;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Trade {

  private final BigDecimal amount;

  private final OffsetDateTime date;

  private final BigDecimal price;

  private final long tid;

  private final String type;
}
