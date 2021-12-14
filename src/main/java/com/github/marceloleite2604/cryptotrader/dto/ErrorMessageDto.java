package com.github.marceloleite2604.cryptotrader.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Getter
public class ErrorMessageDto {

  private final String code;

  private final String message;
}
