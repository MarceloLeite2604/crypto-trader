package com.github.marceloleite2604.cryptotrader.properties;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.Map;

@Getter
@ConfigurationProperties(PropertiesPath.DATABASE)
@Validated
@ConstructorBinding
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DatabaseProperties {

  @NotBlank
  private final String driverClassName;

  @NotBlank
  private final String url;

  @NotBlank
  private final String username;

  @NotBlank
  private final String password;

  @Positive
  @Min(1)
  private final int connections;

  private final Map<String,String> otherConnectionProperties;
}
