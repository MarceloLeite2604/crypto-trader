package com.github.marceloleite2604.cryptotrader.test.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.marceloleite2604.cryptotrader.configuration.ObjectMapperConfiguration;

public class ObjectMapperUtil {

  private static ObjectMapper objectMapper;

  private static ObjectMapper create() {
    final var objectMapperConfiguration = new ObjectMapperConfiguration();
    final var module = objectMapperConfiguration.createModule();
    return objectMapperConfiguration.createObjectMapper(module);
  }

  public static ObjectMapper getObjectMapper() {
    if (objectMapper == null) {
      objectMapper = create();
    }
    return objectMapper;
  }
}
