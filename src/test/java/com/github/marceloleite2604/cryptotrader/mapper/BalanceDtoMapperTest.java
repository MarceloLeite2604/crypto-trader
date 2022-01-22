package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.BalanceDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.BalanceFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BalanceDtoMapperTest {

  private BalanceDtoMapper balanceDtoMapper;

  @BeforeEach
  void setup() {
    balanceDtoMapper = new BalanceDtoMapper();
  }

  @Test
  void shouldMapToBalance() {
    final var expected = BalanceFixture.create();
    final var actual = balanceDtoMapper.mapTo(BalanceDtoFixture.create());

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

}