package com.github.marceloleite2604.cryptotrader.service.analyser.action;

import com.github.marceloleite2604.cryptotrader.model.Action;
import com.github.marceloleite2604.cryptotrader.model.AnalysisContext;
import com.github.marceloleite2604.cryptotrader.service.analyser.action.condition.ActionCondition;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActionService {

  private final ActionCondition first;

  public ActionService(List<ActionCondition> actionConditions) {
    this.first = createChain(actionConditions);
  }

  private ActionCondition createChain(List<ActionCondition> actionConditions) {
    ActionCondition first = null;
    ActionCondition previous = null;

    for (ActionCondition actionCondition : actionConditions) {
      if (first == null) {
        first = actionCondition;
      } else {
        previous.setNext(actionCondition);
      }
      previous = actionCondition;
    }

    return first;
  }

  public Optional<Action> check(AnalysisContext context) {
    return first.check(context);
  }
}
