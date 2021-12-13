package com.github.marceloleite2604.cryptotrader.model.candles;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CandlesRequest {

  @NotBlank
  private final String symbol;

  private final CandlePrecision resolution;

  private final OffsetDateTime toTime;

  private final Integer toCount;

  private final OffsetDateTime from;

  private final Integer countback;

  @AssertTrue(message = "Must inform either a \"from\" and \"toTime\" or \"countback\" and \"toCount\" end time.")
  private boolean isValidRequest() {
    return (from != null && toTime != null) ||
      (countback != null && toCount != null);
  }
}
