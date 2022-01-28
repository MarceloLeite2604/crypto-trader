package com.github.marceloleite2604.cryptotrader.test.util.fixture.dto;

import com.github.marceloleite2604.cryptotrader.dto.ErrorMessageDto;
import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class ErrorMessageDtoFixture {

  public static final String CODE = "codeValue";
  public static final String MESSAGE = "messageValue";
  public static final Map<String, String> ADDITIONAL_PROPERTIES = Map.of("additionalPropertyKey", "additionalPropertyValue");

  public static ErrorMessageDto create() {
    return ErrorMessageDto.builder()
      .code(CODE)
      .message(MESSAGE)
      .additionalProperties(ADDITIONAL_PROPERTIES)
      .build();
  }
}
