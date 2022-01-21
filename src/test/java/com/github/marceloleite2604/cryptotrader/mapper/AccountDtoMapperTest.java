package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.account.AccountDtoFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.account.AccountFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AccountDtoMapperTest {

  private AccountDtoMapper accountDtoMapper;

  @BeforeEach
  void setUp() {
    accountDtoMapper = new AccountDtoMapper();
  }

  @Test
  void shouldMapToAccount() {

    final var expected = AccountFixture.create();

    final var actual = accountDtoMapper.mapTo(AccountDtoFixture.create());

    assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
  }

}