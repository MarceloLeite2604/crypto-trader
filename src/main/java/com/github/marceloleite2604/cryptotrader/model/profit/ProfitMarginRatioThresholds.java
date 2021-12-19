package com.github.marceloleite2604.cryptotrader.model.profit;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
@Entity
public class ProfitMarginRatioThresholds {

  @Id
  private final String accountId;

  @Column(nullable = false)
  private final BigDecimal upper;

  @Column(nullable = false)
  private final BigDecimal current;

  @Column(nullable = false)
  private final BigDecimal lower;
}
