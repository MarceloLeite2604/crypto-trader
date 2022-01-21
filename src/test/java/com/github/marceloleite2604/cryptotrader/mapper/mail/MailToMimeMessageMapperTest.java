package com.github.marceloleite2604.cryptotrader.mapper.mail;

import com.github.marceloleite2604.cryptotrader.properties.MailProperties;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.MailFixture;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.Address;
import javax.mail.Message;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MailToMimeMessageMapperTest {

  @InjectMocks
  private MailToMimeMessageMapper mailToMimeMessageMapper;

  @Mock
  private MailProperties mailProperties;

  @Test
  void shouldMapToMimeMessage() throws Exception {
    final var mail = MailFixture.create();

    when(mailProperties.getHost()).thenReturn("hostValue");
    when(mailProperties.getPort()).thenReturn("2367");

    final var mimeMessage = mailToMimeMessageMapper.mapTo(mail);

    assertThat(mimeMessage).isNotNull();

    final var from = mimeMessage.getFrom();
    assertThat(from).hasSize(1);
    final var fromAddress = from[0];
    assertThat(fromAddress.toString()).hasToString(MailFixture.FROM);

    assertThat(mimeMessage.getRecipients(Message.RecipientType.TO))
      .hasSize(MailFixture.RECIPIENTS.size());
    final var toRecipients = Arrays.stream(mimeMessage.getRecipients(Message.RecipientType.TO))
      .map(Address::toString)
      .toList();

    assertThat(toRecipients).containsAll(MailFixture.RECIPIENTS);

    assertThat(mimeMessage.getSubject()).isEqualTo(MailFixture.SUBJECT);

    assertThat(mimeMessage.getContent()).isNotNull();
    final var content = mimeMessage.getContent();
    assertThat(content).isInstanceOf(String.class);
    final var stringContent = (String)content;
    assertThat(stringContent).isEqualTo(MailFixture.TEXT);

  }


}