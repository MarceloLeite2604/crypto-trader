package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.authorize.AuthorizationFixture;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.dto.authorize.GetAuthorizeResponsePayloadFixture;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetAuthorizeResponsePayloadToAuthorizationMapperTest {

  @InjectMocks
  private GetAuthorizeResponsePayloadToAuthorizationMapper getAuthorizeResponsePayloadToAuthorizationMapper;

  @Mock
  private DateTimeUtil dateTimeUtil;

  @Test
  void shouldReturnGetAuthorizeResponsePayload() {
    final var expected = AuthorizationFixture.create();

    when(dateTimeUtil.convertEpochToUtcOffsetDateTime(
      anyLong())).thenReturn(AuthorizationFixture.EXPIRATION);

    final var actual = getAuthorizeResponsePayloadToAuthorizationMapper
      .mapTo(GetAuthorizeResponsePayloadFixture.create());

    assertThat(actual).usingRecursiveComparison()
      .isEqualTo(expected);
  }

}