package com.github.marceloleite2604.cryptotrader.service.mail;

import com.github.marceloleite2604.cryptotrader.properties.MailProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

@Component
@RequiredArgsConstructor
public class CustomAuthenticator extends Authenticator {

  private final MailProperties mailProperties;

  protected PasswordAuthentication getPasswordAuthentication() {
    return new PasswordAuthentication(mailProperties.getUsername(), mailProperties.getPassword());
  }
}
