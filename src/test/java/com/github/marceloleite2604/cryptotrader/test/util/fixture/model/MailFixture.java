package com.github.marceloleite2604.cryptotrader.test.util.fixture.model;

import com.github.marceloleite2604.cryptotrader.model.Mail;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class MailFixture {

  public static final String TEXT = "textValue";
  public static final String SUBJECT = "subjectValue";
  public static final String RECIPIENT = "recipientValue";
  public static final String FROM = "fromValue";
  public static final List<String> RECIPIENTS = List.of(RECIPIENT);

  public static Mail create() {
    return create(FROM);
  }

  public static Mail create(String from) {
    return Mail.builder()
      .text(TEXT)
      .subject(SUBJECT)
      .recipients(RECIPIENTS)
      .from(from)
      .build();
  }
}
