package com.github.marceloleite2604.cryptotrader.repository;

import com.github.marceloleite2604.cryptotrader.model.profit.Profit;
import com.github.marceloleite2604.cryptotrader.model.profit.ProfitId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfitRepository extends JpaRepository<Profit, ProfitId> {
}
