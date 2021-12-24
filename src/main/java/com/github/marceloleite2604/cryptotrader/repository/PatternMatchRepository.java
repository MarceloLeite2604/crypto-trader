package com.github.marceloleite2604.cryptotrader.repository;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PatternMatchRepository extends JpaRepository<PatternMatch, UUID> {

  List<PatternMatch> findByActiveAndCandlePrecisionAndCandleTimeBetween(
    Active active,
    CandlePrecision candlePrecision,
    OffsetDateTime start,
    OffsetDateTime end);

  Optional<PatternMatch> findByActiveAndCandlePrecisionAndCandleTime(
    Active active,
    CandlePrecision candlePrecision,
    OffsetDateTime candleTime);
}
