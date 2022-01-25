package com.github.marceloleite2604.cryptotrader.service.mercadobitcoin;

import com.github.marceloleite2604.cryptotrader.dto.account.BalanceDto;
import com.github.marceloleite2604.cryptotrader.mapper.BalanceDtoMapper;
import com.github.marceloleite2604.cryptotrader.test.util.MockWebServerUtil;
import com.github.marceloleite2604.cryptotrader.test.util.MockedWebClientTests;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.BalanceDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.account.BalanceFixture;
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
class BalanceServiceTest extends MockedWebClientTests {

  @InjectMocks
  private BalanceService balanceService;

  @Mock
  private BalanceDtoMapper balanceDtoMapper;

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  void shouldReturnBalance() throws Exception {

    final var accountId = "accountIdValue";
    final var symbol = "symbolValue";

    final var balanceDtos = List.of(BalanceDtoFixture.create());
    final var expected = List.of(BalanceFixture.create());

    final var uri = new URIBuilder().setPathSegments("accounts", accountId, "balances")
      .addParameter("symbol", symbol)
      .build();

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, uri)
      .thenReturn(HttpStatus.OK, balanceDtos);

    final ArgumentCaptor<List<BalanceDto>> balanceDtosArgumentCaptor = ArgumentCaptor.forClass((Class) List.class);

    when(balanceDtoMapper.mapAllTo(balanceDtosArgumentCaptor.capture())).thenReturn(expected);

    final var actual = balanceService.retrieve(accountId, symbol);

    assertThat(actual).usingDefaultElementComparator()
      .containsAll(expected);

    assertThat(balanceDtosArgumentCaptor.getValue()).usingDefaultElementComparator()
      .usingRecursiveComparison()
      .isEqualTo(balanceDtos);
  }
}