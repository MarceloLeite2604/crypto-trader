package com.github.marceloleite2604.cryptotrader.service.analyser.action.condition.buy;

import com.github.marceloleite2604.cryptotrader.model.AnalysisContext;
import com.github.marceloleite2604.cryptotrader.util.FormatUtil;
import org.springframework.stereotype.Component;

@Component
public class ActionCondition58 extends AbstractBuyActionCondition {

  protected ActionCondition58(FormatUtil formatUtil) {
    super(formatUtil);
  }

  @Override
  protected boolean doCheck(AnalysisContext context) {
    return context.isFiatBalanceAvailable() &&
      context.isActiveBalanceAvailable() &&
      context.isBuyPatternFound() &&
      !context.isSellPatternFound() &&
      context.isOverUpperLimit() &&
      !context.isBelowLowerLimit();
  }
}
