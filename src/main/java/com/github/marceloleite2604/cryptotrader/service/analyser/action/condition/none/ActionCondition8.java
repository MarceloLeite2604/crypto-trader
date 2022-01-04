package com.github.marceloleite2604.cryptotrader.service.analyser.action.condition.none;

import com.github.marceloleite2604.cryptotrader.model.Action;
import com.github.marceloleite2604.cryptotrader.model.AnalysisContext;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ActionCondition8 extends AbstractNoneActionCondition {

  @Override
  protected boolean doCheck(AnalysisContext context) {
    return !context.isFiatBalanceAvailable() &&
      !context.isActiveBalanceAvailable() &&
      context.isBuyPatternFound() &&
      !context.isSellPatternFound() &&
      !context.isOverUpperLimit() &&
      !context.isBelowLowerLimit();
  }
}
