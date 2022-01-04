package com.github.marceloleite2604.cryptotrader.service.analyser.action.condition.buy;

import com.github.marceloleite2604.cryptotrader.model.Side;
import com.github.marceloleite2604.cryptotrader.service.analyser.action.condition.AbstractActionCondition;
import com.github.marceloleite2604.cryptotrader.util.FormatUtil;

public abstract class AbstractBuyActionCondition extends AbstractActionCondition {

  protected AbstractBuyActionCondition(FormatUtil formatUtil) {
    super(Side.BUY, formatUtil);
  }
}
