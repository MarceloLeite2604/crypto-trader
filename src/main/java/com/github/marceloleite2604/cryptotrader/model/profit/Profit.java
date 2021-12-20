package com.github.marceloleite2604.cryptotrader.model.profit;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.converter.ActiveConverter;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Converter;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;

@Builder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
@Entity
public class Profit {

  @EmbeddedId
  private final ProfitId id;

  @Column(nullable = false, precision = 19, scale = 8)
  private final BigDecimal upper;

  @Column(nullable = false, precision = 19, scale = 8)
  private final BigDecimal current;

  @Column(nullable = false, precision = 19, scale = 8)
  private final BigDecimal lower;

  public boolean hasReachedUpperLimit() {
    if (current == null) {
      return false;
    }
    return current.compareTo(upper) >= 0;
  }

  public boolean hasReachedLowerLimit() {
    if (current == null) {
      return false;
    }
    return current.compareTo(lower) <= 0;
  }
}
