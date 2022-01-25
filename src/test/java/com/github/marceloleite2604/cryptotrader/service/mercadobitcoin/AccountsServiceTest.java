package com.github.marceloleite2604.cryptotrader.service.mercadobitcoin;

import com.github.marceloleite2604.cryptotrader.dto.account.AccountDto;
import com.github.marceloleite2604.cryptotrader.mapper.AccountDtoMapper;
import com.github.marceloleite2604.cryptotrader.test.util.MockWebServerUtil;
import com.github.marceloleite2604.cryptotrader.test.util.MockedWebClientTests;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.AccountDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.account.AccountFixture;
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
class AccountsServiceTest extends MockedWebClientTests {

  @InjectMocks
  private AccountsService accountsService;

  @Mock
  private AccountDtoMapper accountDtoMapper;

  @Test
  @SuppressWarnings({"unchecked", "rawtypes"})
  void shouldReturnAccountsList() {
    final var accountDtos = List.of(AccountDtoFixture.create());
    final var account = AccountFixture.create();

    final ArgumentCaptor<List<AccountDto>> accountDtosArgumentCaptor = ArgumentCaptor.forClass((Class) List.class);

    MockWebServerUtil.using(mockWebServer)
      .when(HttpMethod.GET, "accounts")
      .thenReturn(HttpStatus.OK, accountDtos);

    final var expected = List.of(account);

    when(accountDtoMapper.mapAllTo(accountDtosArgumentCaptor.capture())).thenReturn(expected);

    final var actual = accountsService.retrieve();

    assertThat(actual).usingDefaultElementComparator()
      .containsAll(expected);

    assertThat(accountDtosArgumentCaptor.getValue()).usingDefaultElementComparator()
      .usingRecursiveComparison()
      .isEqualTo(accountDtos);
  }
}
