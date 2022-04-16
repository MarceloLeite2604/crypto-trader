package com.github.marceloleite2604.cryptotrader.service.actionexecutor;

import com.github.marceloleite2604.cryptotrader.mapper.mail.ListActionsToMailMapper;
import com.github.marceloleite2604.cryptotrader.mapper.mail.MailToMimeMessageMapper;
import com.github.marceloleite2604.cryptotrader.mapper.mail.ThrowableToMailMapper;
import com.github.marceloleite2604.cryptotrader.model.Action;
import com.github.marceloleite2604.cryptotrader.model.Mail;
import com.github.marceloleite2604.cryptotrader.model.Side;
import com.github.marceloleite2604.cryptotrader.properties.MailProperties;
import com.github.marceloleite2604.cryptotrader.service.ActionService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.Transport;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MailService implements ActionExecutor {

  private final ListActionsToMailMapper listActionsToMailMapper;

  private final MailToMimeMessageMapper mailToMimeMessageMapper;

  private final ThrowableToMailMapper throwableToMailMapper;

  @SneakyThrows
  public void execute(List<Action> actions) {
    final var mail = listActionsToMailMapper.mapTo(actions);
    sendMail(mail);
  }

  @SneakyThrows
  public void send(Throwable throwable) {
    final var mail = throwableToMailMapper.mapTo(throwable);
    sendMail(mail);
  }

  private void sendMail(Mail mail) throws MessagingException {
    final var mimeMessage = mailToMimeMessageMapper.mapTo(mail);
    Transport.send(mimeMessage);
  }

}
