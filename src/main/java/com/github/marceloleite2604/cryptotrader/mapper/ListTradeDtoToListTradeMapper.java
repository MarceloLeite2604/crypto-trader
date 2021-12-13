package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.dto.trades.TradeDto;
import com.github.marceloleite2604.cryptotrader.model.trades.Trade;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ListTradeDtoToListTradeMapper implements Mapper<List<TradeDto>, List<Trade>> {

  private final DateTimeUtil dateTimeUtil;

  @Override
  public List<Trade> mapTo(List<TradeDto> tradeDtos) {

    if (CollectionUtils.isEmpty(tradeDtos)) {
      return Collections.emptyList();
    }

    final var trades = new ArrayList<Trade>(tradeDtos.size());

    for (TradeDto tradeDto : tradeDtos) {

      final var amount = BigDecimal.valueOf(tradeDto.getAmount());

      final var date = dateTimeUtil.convertEpochToUtcOffsetDateTime(tradeDto.getDate());

      final var price = BigDecimal.valueOf(tradeDto.getPrice());

      final var trade = Trade.builder()
        .amount(amount)
        .date(date)
        .price(price)
        .tid(tradeDto.getTid())
        .type(tradeDto.getType())
        .build();

      trades.add(trade);
    }

    return trades;
  }
}
