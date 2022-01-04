package com.github.marceloleite2604.cryptotrader.service.analyser.action.condition;

import com.github.marceloleite2604.cryptotrader.model.Action;
import com.github.marceloleite2604.cryptotrader.model.AnalysisContext;

import java.util.Optional;

public interface ActionCondition {

  Optional<Action> check(AnalysisContext context);

  void setNext(ActionCondition actionCondition);
}
