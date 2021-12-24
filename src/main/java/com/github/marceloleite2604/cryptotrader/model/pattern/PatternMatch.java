package com.github.marceloleite2604.cryptotrader.model.pattern;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
@Entity
public class PatternMatch {

  @Id
  @GeneratedValue
  private final UUID id;

  @Column(nullable = false)
  private final PatternType type;

  @Column(nullable = false)
  private final OffsetDateTime candleTime;

  @Column(nullable = false)
  private final CandlePrecision candlePrecision;

  @Column(nullable = false)
  private final Active active;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    PatternMatch that = (PatternMatch) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
