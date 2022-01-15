package com.github.marceloleite2604.cryptotrader.service.actionexecutor;

import com.github.marceloleite2604.cryptotrader.mapper.mail.MailToMimeMessageMapper;
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

import javax.mail.Transport;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class MailService implements ActionExecutor {

  private final MailProperties mailProperties;

  private final MailToMimeMessageMapper mailToMimeMessageMapper;

  private final ActionService actionService;

  @SneakyThrows
  public void execute(List<Action> actions) {
    final var mail = createMail(actions);
    final var mimeMessage = mailToMimeMessageMapper.mapTo(mail);
    Transport.send(mimeMessage);
  }

  @SneakyThrows
  private Mail createMail(List<Action> actions) {
    final var from = mailProperties.getUsername();

    final var recipients = mailProperties.getRecipients();

    final var buyActions = actionService.retrieveBySide(actions, Side.BUY);

    final var sellActions = actionService.retrieveBySide(actions, Side.SELL);

    final var subject = elaborateSubject(buyActions, sellActions);

    final var text = elaborateText(buyActions, sellActions);

    return Mail.builder()
      .from(from)
      .recipients(recipients)
      .subject(subject)
      .text(text)
      .build();
  }

  private String elaborateSubject(List<Action> buyActions, List<Action> sellActions) {
    final var optionalBuySubjectPart = elaborateSubjectPart(buyActions, Side.BUY);
    final var optionalSellSubjectPart = elaborateSubjectPart(sellActions, Side.SELL);

    final var actionsSummary = Stream.of(optionalBuySubjectPart, optionalSellSubjectPart)
      .filter(Optional::isPresent)
      .map(Optional::get)
      .collect(Collectors.joining(", "));

    final var prefix = "Crypto-trader: ";
    if (StringUtils.isNotBlank(actionsSummary)) {
      return prefix + actionsSummary;
    } else {
      return prefix + "No actions for today";
    }
  }

  private Optional<String> elaborateSubjectPart(List<Action> actions, Side side) {
    if (CollectionUtils.isEmpty(actions)) {
      return Optional.empty();
    }

    final var sideText = side.name()
      .toLowerCase(Locale.ROOT);
    final var order = actions.size() > 1 ? "orders" : "order";

    final var text = String.format("%d %s %s", actions.size(), sideText, order);

    return Optional.of(text);
  }


  private String elaborateText(List<Action> buyActions, List<Action> sellActions) {
    final var buySentences = elaborateSentences(buyActions);

    final var sellSentences = elaborateSentences(sellActions);

    final var text = Stream.concat(buySentences.stream(), sellSentences.stream())
      .collect(Collectors.joining("\n\n"));

    if (StringUtils.isBlank(text)) {
      return "No actions for today. Enjoy your day off! 😎";
    }

    return text;
  }

  private List<String> elaborateSentences(List<Action> actions) {
    return actions.stream()
      .map(this::elaborateTextParagraph)
      .toList();
  }

  private String elaborateTextParagraph(Action action) {
    final var active = action.getActive()
      .getName();

    final var side = action.getSide()
      .toString()
      .toLowerCase(Locale.ROOT);

    final var reasons = action.getReasons()
      .stream()
      .map(argument -> "\t\t➡ " + argument)
      .collect(Collectors.joining("\n"));

    return String.format("💡 We advise you to %s %s due to the following ", side, active) +
      (action.getReasons()
        .size() > 1 ? "reasons" : "reason") +
      ":\n" +
      reasons;
  }
}
