package com.github.marceloleite2604.cryptotrader.service.mercadobitcoin;

import com.github.marceloleite2604.cryptotrader.dto.account.PositionDto;
import com.github.marceloleite2604.cryptotrader.mapper.PositionDtoMapper;
import com.github.marceloleite2604.cryptotrader.test.util.MockWebServerUtil;
import com.github.marceloleite2604.cryptotrader.test.util.MockedWebClientTests;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.AccountDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.PositionDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.GetSymbolsResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.account.PositionFixture;
import lombok.SneakyThrows;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PositionServiceTest extends MockedWebClientTests {

  @InjectMocks
  private PositionService positionService;

  @Mock
  private PositionDtoMapper positionDtoMapper;

  @Test
  @SuppressWarnings("unchecked")
  void shouldReturnPositions() {

    final var positionDtos = List.of(PositionDtoFixture.create());

    final var expected = List.of(PositionFixture.create());

    final var uri = buildGetPositionsUri();

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, positionDtos);

    final ArgumentCaptor<List<PositionDto>> positionDtosArgumentCaptor = ArgumentCaptor.forClass(List.class);

    when(positionDtoMapper.mapAllTo(positionDtosArgumentCaptor.capture()))
      .thenReturn(expected);

    final var actual = positionService.retrieve(
      AccountDtoFixture.ID,
      GetSymbolsResponsePayloadFixture.SYMBOL_VALUE);

    assertThat(actual).usingDefaultElementComparator()
      .usingRecursiveComparison()
      .isEqualTo(expected);

    assertThat(positionDtosArgumentCaptor.getValue()).usingDefaultElementComparator()
      .usingRecursiveComparison()
      .isEqualTo(positionDtos);
  }

  @SneakyThrows
  private String buildGetPositionsUri() {
    return new URIBuilder()
      .setPathSegments("accounts", AccountDtoFixture.ID, "positions")
      .addParameter("symbol", GetSymbolsResponsePayloadFixture.SYMBOL_VALUE)
      .build()
      .toASCIIString();
  }

}