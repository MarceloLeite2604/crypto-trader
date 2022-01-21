package com.github.marceloleite2604.cryptotrader.mapper.mail;

import com.github.marceloleite2604.cryptotrader.properties.MailProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomAuthenticatorTest {

  @InjectMocks
  private CustomAuthenticator customAuthenticator;

  @Mock
  private MailProperties mailProperties;

  @Test
  void shouldReturnPasswordAuthenticationWithMailPropertiesValues() {

    final var username = "usernameValue";
    final var password = "passwordValue";

    when(mailProperties.getUsername()).thenReturn(username);
    when(mailProperties.getPassword()).thenReturn(password);

    final var passwordAuthentication = customAuthenticator.getPasswordAuthentication();

    assertThat(passwordAuthentication).isNotNull();
    assertThat(passwordAuthentication.getUserName()).isEqualTo(username);
    assertThat(passwordAuthentication.getPassword()).isEqualTo(password);
  }

}