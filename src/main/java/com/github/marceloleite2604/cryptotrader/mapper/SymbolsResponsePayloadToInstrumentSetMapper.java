package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.dto.symbols.SymbolResponsePayload;
import com.github.marceloleite2604.cryptotrader.model.Instrument;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TimeZone;

@Component
public class SymbolsResponsePayloadToInstrumentSetMapper
  implements Mapper<SymbolResponsePayload, Set<Instrument>> {

  @Override
  public Set<Instrument> mapTo(SymbolResponsePayload symbolResponsePayload) {

    if (symbolResponsePayload == null) {
      return Collections.emptySet();
    }

    if (CollectionUtils.isEmpty(symbolResponsePayload.getSymbol())) {
      return Collections.emptySet();
    }

    Set<Instrument> instruments = new HashSet<>();

    for (int count = 0; count < symbolResponsePayload.getSymbol()
      .size(); count++) {
      final var instrument = createInstrument(symbolResponsePayload, count);
      instruments.add(instrument);
    }

    return instruments;
  }

  private Instrument createInstrument(SymbolResponsePayload symbolResponsePayload, int count) {
    final var baseCurrency = symbolResponsePayload.getBaseCurrency()
      .get(count);
    final var currency = symbolResponsePayload.getCurrency()
      .get(count);
    final var description = symbolResponsePayload.getDescription()
      .get(count);
    final var exchangeListed = symbolResponsePayload.getExchangeListed()
      .get(count);
    final var exchangeTraded = symbolResponsePayload.getExchangeTraded()
      .get(count);
    final var minMovement = symbolResponsePayload.getMinMovement()
      .get(count);
    final var priceScale = symbolResponsePayload.getPriceScale()
      .get(count);
    final var sessionRegular = symbolResponsePayload.getSessionRegular()
      .get(count);
    final var symbol = symbolResponsePayload.getSymbol()
      .get(count);
    final var timezone = TimeZone.getTimeZone(symbolResponsePayload.getTimezone()
      .get(count));
    final var type = symbolResponsePayload.getType()
      .get(count);

    return Instrument.builder()
      .baseCurrency(baseCurrency)
      .currency(currency)
      .description(description)
      .exchangeListed(exchangeListed)
      .exchangeTraded(exchangeTraded)
      .minMovement(minMovement)
      .priceScale(priceScale)
      .sessionRegular(sessionRegular)
      .symbol(symbol)
      .timezone(timezone)
      .type(type)
      .build();
  }
}
