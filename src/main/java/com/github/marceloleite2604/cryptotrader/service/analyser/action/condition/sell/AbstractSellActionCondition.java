package com.github.marceloleite2604.cryptotrader.service.analyser.action.condition.sell;

import com.github.marceloleite2604.cryptotrader.model.Side;
import com.github.marceloleite2604.cryptotrader.service.analyser.action.condition.AbstractActionCondition;
import com.github.marceloleite2604.cryptotrader.util.FormatUtil;

public abstract class AbstractSellActionCondition extends AbstractActionCondition {

  protected AbstractSellActionCondition(FormatUtil formatUtil) {
    super(Side.SELL, formatUtil);
  }
}
