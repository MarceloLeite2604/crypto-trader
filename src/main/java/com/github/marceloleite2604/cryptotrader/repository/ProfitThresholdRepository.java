package com.github.marceloleite2604.cryptotrader.repository;

import com.github.marceloleite2604.cryptotrader.model.profit.ProfitMarginRatioThresholds;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitThresholdRepository extends JpaRepository<ProfitMarginRatioThresholds, String> {
}
