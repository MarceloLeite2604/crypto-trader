package com.github.marceloleite2604.cryptotrader.service.mercadobitcoin;

import com.github.marceloleite2604.cryptotrader.dto.symbols.GetSymbolsResponsePayload;
import com.github.marceloleite2604.cryptotrader.mapper.GetSymbolsResponsePayloadToInstrumentSetMapper;
import com.github.marceloleite2604.cryptotrader.test.util.MockWebServerUtil;
import com.github.marceloleite2604.cryptotrader.test.util.MockedWebClientTests;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.orders.GetSymbolsResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.InstrumentFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InstrumentServiceTest extends MockedWebClientTests {

  @InjectMocks
  private InstrumentService instrumentService;

  @Mock
  private GetSymbolsResponsePayloadToInstrumentSetMapper getSymbolsResponsePayloadToInstrumentSetMapper;

  @Test
  void shouldReturnInstrumentSet() {

    final var getSymbolsResponsePayload = GetSymbolsResponsePayloadFixture.create();

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, "symbols")
      .thenReturn(HttpStatus.OK, getSymbolsResponsePayload);

    final var getSymbolsResponsePayloadArgumentCaptor = ArgumentCaptor.forClass(GetSymbolsResponsePayload.class);

    final var expected = Set.of(InstrumentFixture.create());

    when(getSymbolsResponsePayloadToInstrumentSetMapper.mapTo(getSymbolsResponsePayloadArgumentCaptor.capture()))
      .thenReturn(expected);

    final var actual = instrumentService.retrieveAll();

    assertThat(actual).usingDefaultElementComparator()
      .usingRecursiveComparison()
      .isEqualTo(expected);

    assertThat(getSymbolsResponsePayloadArgumentCaptor.getValue())
      .usingRecursiveComparison()
      .isEqualTo(getSymbolsResponsePayload);
  }
}