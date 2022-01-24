package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.ActiveFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.InstrumentFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class InstrumentToActiveMapperTest {

  private InstrumentToActiveMapper instrumentToActiveMapper;

  @BeforeEach
  void setUp() {
    instrumentToActiveMapper = new InstrumentToActiveMapper();
  }

  @Test
  void shouldReturnActive() {
    final var expected = ActiveFixture.retrieve();

    final var actual = instrumentToActiveMapper.mapTo(InstrumentFixture.create());

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }
}