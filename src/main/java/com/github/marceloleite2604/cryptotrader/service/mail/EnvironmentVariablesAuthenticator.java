package com.github.marceloleite2604.cryptotrader.service.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class EnvironmentVariablesAuthenticator extends Authenticator {

  protected PasswordAuthentication getPasswordAuthentication() {
    final var username = System.getenv("EMAIL_USERNAME");
    final var password = System.getenv("EMAIL_PASSWORD");
    return new PasswordAuthentication(username, password);
  }
}
