package com.github.marceloleite2604.cryptotrader.service.analyser.action.condition;

import com.github.marceloleite2604.cryptotrader.model.Action;
import com.github.marceloleite2604.cryptotrader.model.AnalysisContext;
import com.github.marceloleite2604.cryptotrader.model.Side;
import com.github.marceloleite2604.cryptotrader.util.FormatUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractActionCondition implements ActionCondition {

  private final Side side;

  private final FormatUtil formatUtil;

  private ActionCondition next;

  @Override
  public void setNext(ActionCondition next) {
    this.next = next;
  }

  public Optional<Action> check(AnalysisContext context) {
    if (doCheck(context)) {
      return elaborateAction(context);
    }

    if (next != null) {
      return next.check(context);
    }

    log.warn("Could not find an action condition for context: {}", context);
    return Optional.empty();
  }

  protected Optional<Action> elaborateAction(AnalysisContext context) {

    final var arguments = retrieveArguments(context);

    final var summary = elaborateSummary(context, arguments);

    final var action = Action.builder()
      .active(context.getActive())
      .arguments(arguments)
      .summary(summary)
      .side(side)
      .build();

    return Optional.of(action);
  }

  private List<String> retrieveArguments(AnalysisContext analysisContext) {
    final var arguments = analysisContext.retrievePatternMatchesBySide(side)
      .stream()
      .map(patternMatch -> String.format("%s pattern found on %s range.", patternMatch.getType()
        .getName(), patternMatch.getCandlePrecision()
        .getDescription()))
      .collect(Collectors.toCollection(ArrayList::new));

    final var profit = analysisContext.getProfit();

    if (profit.hasReachedUpperLimit()) {
      final var message = String.format(
        "Profit has reached the upper limit of %s. It is now %s.",
        formatUtil.toPercentage(profit.getUpper()),
        formatUtil.toPercentage(profit.getCurrent()));
      arguments.add(message);
    }

    if (profit.hasReachedUpperLimit()) {
      final var message = String.format(
        "Profit has reached the upper limit of %s. It is now %s.",
        formatUtil.toPercentage(profit.getUpper()),
        formatUtil.toPercentage(profit.getCurrent()));
      arguments.add(message);
    }

    return arguments;
  }

  private String elaborateSummary(AnalysisContext analysisContext, List<String> arguments) {

    String summary;
    if (arguments.size() > 1) {
      summary = String.format("Strong advice to %s %s", side.name()
        .toLowerCase(), analysisContext.getActive()
        .getName());
    } else {
      summary = String.format("Advice to %s %s", side.name()
        .toLowerCase(), analysisContext.getActive()
        .getName());
    }

    return summary;
  }

  protected abstract boolean doCheck(AnalysisContext context);
}
