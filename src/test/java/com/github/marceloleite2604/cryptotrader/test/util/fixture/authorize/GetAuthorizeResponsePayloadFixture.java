package com.github.marceloleite2604.cryptotrader.test.util.fixture.authorize;

import com.github.marceloleite2604.cryptotrader.dto.authorize.GetAuthorizeResponsePayload;
import lombok.experimental.UtilityClass;

@UtilityClass
public class GetAuthorizeResponsePayloadFixture {

  public static final int EXPIRATION = 1642548317;

  public static GetAuthorizeResponsePayload create() {
    return GetAuthorizeResponsePayload.builder()
      .accessToken(AuthorizationFixture.TOKEN)
      .expiration(EXPIRATION)
      .build();
  }
}
