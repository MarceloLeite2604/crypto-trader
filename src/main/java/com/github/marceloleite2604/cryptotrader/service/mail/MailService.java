package com.github.marceloleite2604.cryptotrader.service.mail;

import com.github.marceloleite2604.cryptotrader.model.Action;
import com.github.marceloleite2604.cryptotrader.model.Side;
import com.github.marceloleite2604.cryptotrader.model.pattern.PatternMatch;
import com.github.marceloleite2604.cryptotrader.model.profit.Profit;
import com.github.marceloleite2604.cryptotrader.properties.MailProperties;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import com.github.marceloleite2604.cryptotrader.util.FormatUtil;
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
import java.util.stream.Collectors;

@Service
public class MailService {

  private final Session session;

  private final MailProperties mailProperties;

  private final DateTimeUtil dateTimeUtil;

  private final List<InternetAddress> recipients;

  private final FormatUtil formatUtil;

  public MailService(
    MailProperties mailProperties,
    CustomAuthenticator customAuthenticator,
    DateTimeUtil dateTimeUtil,
    FormatUtil formatUtil) {
    this.session = createSession(mailProperties, customAuthenticator);
    this.mailProperties = mailProperties;
    this.dateTimeUtil = dateTimeUtil;
    this.recipients = createRecipients(mailProperties);
    this.formatUtil = formatUtil;
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

    final var active = patternMatch.getActive()
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

  @SneakyThrows
  public void send(Profit profit, Side side) {
    final var mimeMessage = createEmail(profit, side);
    Transport.send(mimeMessage);
  }

  @SneakyThrows
  private MimeMessage createEmail(Profit profit, Side side) {

    final var mimeMessage = new MimeMessage(session);

    final var address = new InternetAddress(mailProperties.getUsername());

    final var active = profit.getId()
      .getActive()
      .getName();

    final var action = side
      .toString()
      .toLowerCase(Locale.ROOT);

    final var percentage = formatUtil.toPercentage(profit.getCurrent());

    String bodyStringBuilder = String.format("Your profit has reached %s.", percentage) +
      "\n" +
      String.format("It is a good time to %s %s.", action, active);

    mimeMessage.setFrom(address);
    recipients.forEach(recipient -> {
      try {
        mimeMessage.addRecipient(MimeMessage.RecipientType.TO, recipient);
      } catch (Exception exception) {
        final var message = String.format("Exception thrown while adding a \"%s\" recipient address on mail.", recipient.getAddress());
        throw new IllegalStateException(message, exception);
      }
    });

    mimeMessage.setSubject(String.format("Crypto-trade - %s threshold reached", side.toString()
      .toLowerCase(Locale.ROOT)));
    mimeMessage.setText(bodyStringBuilder);
    return mimeMessage;
  }

  @SneakyThrows
  public void send(Action action) {
    final var mimeMessage = createEmail(action);
    Transport.send(mimeMessage);
  }

  @SneakyThrows
  private MimeMessage createEmail(Action action) {
    final var mimeMessage = new MimeMessage(session);

    final var address = new InternetAddress(mailProperties.getUsername());

    final var active = action.getActive()
      .getName();

    final var side = action.getSide()
      .toString()
      .toLowerCase(Locale.ROOT);

    final var arguments = action.getArguments()
      .stream()
      .map(argument -> "\t- " + argument)
      .collect(Collectors.joining("\n"));

    String bodyStringBuilder = String.format("We advise you to %s %s due to the following ", side, active) +
      (action.getArguments()
        .size() > 1 ? "arguments" : "argument") +
      ":\n" +
      arguments;

    mimeMessage.setFrom(address);
    recipients.forEach(recipient -> {
      try {
        mimeMessage.addRecipient(MimeMessage.RecipientType.TO, recipient);
      } catch (Exception exception) {
        final var message = String.format("Exception thrown while adding a \"%s\" recipient address on mail.", recipient.getAddress());
        throw new IllegalStateException(message, exception);
      }
    });

    mimeMessage.setSubject("Crypto-trader: " + action.getSummary());
    mimeMessage.setText(bodyStringBuilder);
    return mimeMessage;
  }
}
