package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.dto.symbols.GetSymbolsResponsePayload;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.GetSymbolsResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.InstrumentFixture;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.Set;
import java.util.TimeZone;

import static org.assertj.core.api.Assertions.assertThat;

class GetSymbolsResponsePayloadToInstrumentSetMapperTest {

  private GetSymbolsResponsePayloadToInstrumentSetMapper getSymbolsResponsePayloadToInstrumentSetMapper;

  @BeforeEach
  void setUp() {
    getSymbolsResponsePayloadToInstrumentSetMapper = new GetSymbolsResponsePayloadToInstrumentSetMapper();
  }

  @Test
  void shouldReturnInstrumentSet() {
    final var expected = Set.of(InstrumentFixture.create());

    final var actual = getSymbolsResponsePayloadToInstrumentSetMapper
      .mapTo(GetSymbolsResponsePayloadFixture.create());

    final Comparator<TimeZone> timeZoneComparator = (tz1, tz2) -> tz1.equals(tz2) ? 0 : 1;

    final var recursiveComparisonConfiguration = RecursiveComparisonConfiguration
      .builder()
      .withComparatorForType(timeZoneComparator, TimeZone.class)
      .build();

    assertThat(actual).usingDefaultElementComparator()
      .usingRecursiveComparison(recursiveComparisonConfiguration)
      .isEqualTo(expected);
  }

  @Test
  void shouldReturnEmptySetWhenGetSymbolsResponsePayloadIsNull() {
    final var actual = getSymbolsResponsePayloadToInstrumentSetMapper
      .mapTo(null);

    assertThat(actual).isEmpty();
  }

  @Test
  void shouldReturnEmptySetWhenGetSymbolsResponsePayloadHasNoSymbol() {
    final var actual = getSymbolsResponsePayloadToInstrumentSetMapper
      .mapTo(GetSymbolsResponsePayload.builder()
        .build());

    assertThat(actual).isEmpty();
  }

}