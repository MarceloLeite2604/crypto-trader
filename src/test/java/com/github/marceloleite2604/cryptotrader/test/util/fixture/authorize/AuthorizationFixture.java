package com.github.marceloleite2604.cryptotrader.test.util.fixture.authorize;

import com.github.marceloleite2604.cryptotrader.dto.authorize.Authorization;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@UtilityClass
public class AuthorizationFixture {

  public static final String TOKEN = "tokenValue";
  public static final OffsetDateTime EXPIRATION = OffsetDateTime.of(
    LocalDateTime.of(2022, 1, 18, 23, 25, 17),
    ZoneOffset.UTC);

  public static Authorization create() {
    return Authorization.builder()
      .token(TOKEN)
      .expiration(EXPIRATION)
      .build();
  }
}
