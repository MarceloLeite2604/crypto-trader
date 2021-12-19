package com.github.marceloleite2604.cryptotrader.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.util.List;
import java.util.Map;

@Setter
@Getter
@NoArgsConstructor(force = true)
@ConfigurationProperties(PropertiesPath.DATABASE)
@Validated
@Component
public class DatabaseProperties {

  @NotBlank
  private String driverClassName;

  @NotBlank
  private String url;

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @Positive
  @Min(1)
  private int connections;

  private Map<String,String> otherConnectionProperties;
}
