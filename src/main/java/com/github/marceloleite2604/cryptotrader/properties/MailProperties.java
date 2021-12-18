package com.github.marceloleite2604.cryptotrader.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@ConfigurationProperties(PropertiesPath.MAIL)
@Component
@Validated
@Setter
@Getter
@NoArgsConstructor(force = true)
public class MailProperties {

  @NotBlank
  private String username;

  @NotBlank
  private String password;

  @NotBlank
  private String host;

  @NotBlank
  private String port;

  @NotEmpty
  private List<String> recipients;
}
