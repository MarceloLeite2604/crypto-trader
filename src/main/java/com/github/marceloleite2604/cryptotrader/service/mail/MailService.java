package com.github.marceloleite2604.cryptotrader.service.mail;

import com.github.marceloleite2604.cryptotrader.model.Symbol;
import com.github.marceloleite2604.cryptotrader.model.patterns.PatternMatch;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Properties;

@Service
@RequiredArgsConstructor
public class MailService {

  public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss O");

  private Properties properties;

  @SneakyThrows
  public void send(PatternMatch patternMatch) {
    final var mimeMessage = createEmail(patternMatch);
    Transport.send(mimeMessage);
  }

  @SneakyThrows
  private MimeMessage createEmail(PatternMatch patternMatch) {

    Session session = Session.getInstance(retrieveProperties(), new EnvironmentVariablesAuthenticator());
    MimeMessage mimeMessage = new MimeMessage(session);

    final var address = new InternetAddress(System.getenv("EMAIL_USERNAME"));

    final var patternName = patternMatch.getType()
      .getName();

    final var time = DATE_TIME_FORMATTER
      .format(patternMatch.getCandleTime());

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
    mimeMessage.addRecipient(MimeMessage.RecipientType.TO, address);
    mimeMessage.setSubject(String.format("Crypto-trade - %s found", patternName));
    mimeMessage.setText(bodyStringBuilder);
    return mimeMessage;
  }

  private Properties retrieveProperties() {
    if (properties == null) {
      properties = new Properties();
      properties.put("mail.smtp.host", System.getenv("EMAIL_HOST"));
      properties.put("mail.smtp.port", System.getenv("EMAIL_PORT"));
      properties.put("mail.smtp.ssl.enable", "true");
      properties.put("mail.smtp.auth", "true");
    }
    return properties;
  }
}
