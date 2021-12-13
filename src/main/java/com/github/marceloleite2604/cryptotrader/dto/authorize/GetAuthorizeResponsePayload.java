package com.github.marceloleite2604.cryptotrader.dto.authorize;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Getter
public class GetAuthorizeResponsePayload {

    @JsonProperty("access_token")
    private final String accessToken;

    private final long expiration;
}
