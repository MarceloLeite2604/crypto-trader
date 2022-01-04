package com.github.marceloleite2604.cryptotrader.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.Duration;
import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class OffsetDateTimeRange {

  private final OffsetDateTime start;

  private final OffsetDateTime end;

  public boolean isBetween(OffsetDateTime offsetDateTime) {
    return start.compareTo(offsetDateTime) <= 0 &&
      end.compareTo(offsetDateTime) > 0;
  }

  public Duration getDuration() {
    return Duration.between(start, end);
  }

  public boolean equals(final Object o) {
    if (o == this) return true;
    if (!(o instanceof OffsetDateTimeRange)) return false;
    final OffsetDateTimeRange other = (OffsetDateTimeRange) o;
    if (!other.canEqual(this)) return false;
    final Object this$start = this.getStart();
    final Object other$start = other.getStart();
    if (this$start == null ? other$start != null : !this$start.equals(other$start)) return false;
    final Object this$end = this.getEnd();
    final Object other$end = other.getEnd();
    return this$end == null ? other$end == null : this$end.equals(other$end);
  }

  protected boolean canEqual(final Object other) {
    return other instanceof OffsetDateTimeRange;
  }

  public int hashCode() {
    final int PRIME = 59;
    int result = 1;
    final Object $start = this.getStart();
    result = result * PRIME + ($start == null ? 43 : $start.hashCode());
    final Object $end = this.getEnd();
    result = result * PRIME + ($end == null ? 43 : $end.hashCode());
    return result;
  }
}
