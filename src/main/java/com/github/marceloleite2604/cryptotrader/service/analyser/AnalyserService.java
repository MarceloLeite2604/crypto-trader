package com.github.marceloleite2604.cryptotrader.service.analyser;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.model.AnalysisContext;
import com.github.marceloleite2604.cryptotrader.model.OffsetDateTimeRange;
import com.github.marceloleite2604.cryptotrader.model.account.Account;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlePrecision;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.service.ProfitService;
import com.github.marceloleite2604.cryptotrader.service.analyser.action.ActionService;
import com.github.marceloleite2604.cryptotrader.service.candle.CandleService;
import com.github.marceloleite2604.cryptotrader.service.mail.MailService;
import com.github.marceloleite2604.cryptotrader.service.mercadobitcoin.MercadoBitcoinService;
import com.github.marceloleite2604.cryptotrader.service.pattern.PatternService;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AnalyserService {

  private final MercadoBitcoinService mercadoBitcoinService;

  private final PatternService patternService;

  private final CandleService candleService;

  private final MailService mailService;

  private final ProfitService profitService;

  private final DateTimeUtil dateTimeUtil;

  private final ActionService actionService;

  private Account account;

  public void analyse(Active active) {
    final AnalysisContext context = elaborateContext(active);

    actionService.check(context)
      .ifPresent(mailService::send);

    if (context.isActiveBalanceAvailable()) {
      log.debug("{} ({})",
        profitService.toStringPrice(context.getProfit()),
        profitService.toStringPercentage(context.getProfit()));
    } else {
      log.debug("{} analysed.", active.getName());
    }

    profitService.updateAndSave(context.getProfit());
  }

  private AnalysisContext elaborateContext(Active active) {
    final var accountId = retrieveAccount().getId();

    final var shortRangeCandles = retrieveShortRangeCandles(active);
    final var longRangeCandles = retrieveLongRangeCandles(active);
    final var shortRangePatterns = patternService.check(active, shortRangeCandles);
    final var longRangePatterns = patternService.check(active, longRangeCandles);
    final var profit = profitService.retrieve(accountId, active);
    final var activeBalance = mercadoBitcoinService.retrieveBalance(accountId, active.getBase());
    final var fiatBalance = mercadoBitcoinService.retrieveBalance(accountId, active.getQuote());

    return AnalysisContext.builder()
      .active(active)
      .activeBalance(activeBalance)
      .fiatBalance(fiatBalance)
      .longRangeCandles(longRangeCandles)
      .shortRangeCandles(shortRangeCandles)
      .shortRangePatterns(shortRangePatterns)
      .longRangePatterns(longRangePatterns)
      .profit(profit)
      .build();
  }

  private List<Candle> retrieveLongRangeCandles(Active active) {
    return retrieveCandles(CandlePrecision.FIFTEEN_MINUTES, active);
  }

  private List<Candle> retrieveShortRangeCandles(Active active) {
    return retrieveCandles(CandlePrecision.THREE_MINUTES, active);
  }

  private List<Candle> retrieveCandles(CandlePrecision resolution, Active active) {
    final var end = dateTimeUtil.truncateTo(
      OffsetDateTime.now(ZoneOffset.UTC),
      resolution.getDuration());

    var start = end
      .minus(resolution.getDuration()
        .multipliedBy(12));

    final var range = OffsetDateTimeRange.builder()
      .start(start)
      .end(end)
      .build();

    final var candlesRequest = createCandlesRequest(range, resolution, active);
    log.debug("Retrieving candles: {}", candlesRequest);
    return candleService.retrieveCandles(candlesRequest);
  }

  private CandlesRequest createCandlesRequest(OffsetDateTimeRange range, CandlePrecision resolution, Active active) {
    return CandlesRequest.builder()
      .active(active)
      .resolution(resolution)
      .toTime(range.getEnd())
      .from(range.getStart())
      .build();
  }

  private Account retrieveAccount() {
    if (account == null) {
      account = mercadoBitcoinService.retrieveAccount();
    }
    return account;
  }
}
