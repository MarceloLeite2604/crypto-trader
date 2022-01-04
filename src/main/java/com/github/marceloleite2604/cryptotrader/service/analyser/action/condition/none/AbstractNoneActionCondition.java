package com.github.marceloleite2604.cryptotrader.service.analyser.action.condition.none;

import com.github.marceloleite2604.cryptotrader.model.Action;
import com.github.marceloleite2604.cryptotrader.model.AnalysisContext;
import com.github.marceloleite2604.cryptotrader.service.analyser.action.condition.AbstractActionCondition;

import java.util.Optional;

public abstract class AbstractNoneActionCondition extends AbstractActionCondition {

  protected AbstractNoneActionCondition() {
    super(null, null);
  }

  @Override
  protected Optional<Action> elaborateAction(AnalysisContext context) {
    return Optional.empty();
  }
}
