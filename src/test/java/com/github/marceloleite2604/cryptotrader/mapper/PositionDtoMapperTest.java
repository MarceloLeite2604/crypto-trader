package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.PositionDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.account.PositionFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PositionDtoMapperTest {

  private PositionDtoMapper positionDtoMapper;

  @BeforeEach
  void setUp() {
    positionDtoMapper = new PositionDtoMapper();
  }

  @Test
  void shouldReturnPosition() {
    final var expected = PositionFixture.create();
    final var actual = positionDtoMapper.mapTo(PositionDtoFixture.create());

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

}