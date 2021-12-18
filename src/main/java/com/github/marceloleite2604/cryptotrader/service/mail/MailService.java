package com.github.marceloleite2604.cryptotrader.service.mail;

import com.github.marceloleite2604.cryptotrader.model.Symbol;
import com.github.marceloleite2604.cryptotrader.model.patterns.PatternMatch;
import com.github.marceloleite2604.cryptotrader.properties.MailProperties;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

@Service
public class MailService {

  private final Session session;

  private final MailProperties mailProperties;

  private final DateTimeUtil dateTimeUtil;

  private final List<InternetAddress> recipients;

  public MailService(
    MailProperties mailProperties,
    CustomAuthenticator customAuthenticator,
    DateTimeUtil dateTimeUtil) {
    this.session = createSession(mailProperties, customAuthenticator);
    this.mailProperties = mailProperties;
    this.dateTimeUtil = dateTimeUtil;
    this.recipients = createRecipients(mailProperties);
  }

  private List<InternetAddress> createRecipients(MailProperties mailProperties) {
    return mailProperties.getRecipients()
      .stream()
      .map(recipient -> {
        try {
          return new InternetAddress(recipient);
        } catch (AddressException exception) {
          final var message = String.format("Exception thrown while trying to create an internet address with \"%s\" value.", recipient);
          throw new IllegalStateException(message, exception);
        }
      })
      .toList();
  }

  private Session createSession(MailProperties mailProperties, CustomAuthenticator customAuthenticator) {
    final var properties = createProperties(mailProperties);
    return Session.getInstance(properties, customAuthenticator);
  }

  @SneakyThrows
  public void send(PatternMatch patternMatch) {
    final var mimeMessage = createEmail(patternMatch);
    Transport.send(mimeMessage);
  }

  @SneakyThrows
  private MimeMessage createEmail(PatternMatch patternMatch) {

    final var mimeMessage = new MimeMessage(session);

    final var address = new InternetAddress(mailProperties.getUsername());

    final var patternName = patternMatch.getType()
      .getName();

    final var time = dateTimeUtil.formatOffsetDateTimeAsString(patternMatch.getCandleTime());

    final var active = Symbol.findByValue(patternMatch.getSymbol())
      .getName();

    final var action = patternMatch.getType()
      .getSide()
      .toString()
      .toLowerCase(Locale.ROOT);

    String bodyStringBuilder = String.format("A %s pattern has been found at %s.", patternName, time) +
      "\n" +
      String.format("Seems like a good time to %s %s.", action, active);

    mimeMessage.setFrom(address);
    recipients.forEach(recipient -> {
      try {
        mimeMessage.addRecipient(MimeMessage.RecipientType.TO, recipient);
      } catch (Exception exception) {
        final var message = String.format("Exception thrown while adding a \"%s\" recipient address on mail.", recipient.getAddress());
        throw new IllegalStateException(message, exception);
      }
    });

    mimeMessage.setSubject(String.format("Crypto-trade - %s found", patternName));
    mimeMessage.setText(bodyStringBuilder);
    return mimeMessage;
  }

  private Properties createProperties(MailProperties mailProperties) {
    final var properties = new Properties();
    properties.put("mail.smtp.host", mailProperties.getHost());
    properties.put("mail.smtp.port", mailProperties.getPort());
    properties.put("mail.smtp.ssl.enable", "true");
    properties.put("mail.smtp.auth", "true");
    return properties;
  }
}
