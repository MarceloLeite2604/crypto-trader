package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.PostOrderRequestPayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.orders.PlaceOrderRequestFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlaceOrderRequestToPostOrderRequestPayloadMapperTest {

  private PlaceOrderRequestToPostOrderRequestPayloadMapper placeOrderRequestToPostOrderRequestPayloadMapper;

  @BeforeEach
  void setUp() {
    placeOrderRequestToPostOrderRequestPayloadMapper = new PlaceOrderRequestToPostOrderRequestPayloadMapper();
  }

  @Test
  void shouldReturnPostOrderRequestPayload() {
    final var expected = PostOrderRequestPayloadFixture.create();
    final var actual = placeOrderRequestToPostOrderRequestPayloadMapper.mapTo(PlaceOrderRequestFixture.create());

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

}