package com.github.marceloleite2604.cryptotrader.mapper;

import com.github.marceloleite2604.cryptotrader.dto.authorize.Authorization;
import com.github.marceloleite2604.cryptotrader.dto.authorize.AuthorizeResponsePayload;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuthorizeResponsePayloadToAuthorizationMapper implements
  Mapper<AuthorizeResponsePayload, Authorization> {

  private final DateTimeUtil dateTimeUtil;

  @Override
  public Authorization mapTo(AuthorizeResponsePayload authorizeResponsePayload) {
    return Authorization.builder()
      .token(authorizeResponsePayload.getAccessToken())
      .expiration(
        dateTimeUtil.convertEpochToUtcOffsetDateTime(
          authorizeResponsePayload.getExpiration()))
      .build();
  }
}
