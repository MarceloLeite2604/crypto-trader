package com.github.marceloleite2604.cryptotrader.service.mercadobitcoin;

import com.github.marceloleite2604.cryptotrader.dto.candle.GetCandleResponsePayload;
import com.github.marceloleite2604.cryptotrader.mapper.GetCandleResponsePayloadToListCandleMapper;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import com.github.marceloleite2604.cryptotrader.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
class CandlesService {

  private final WebClient mbAuthenticatedWebClient;

  private final DateTimeUtil dateTimeUtil;

  private final ValidationUtil validationUtil;

  public List<Candle> retrieve(CandlesRequest candlesRequest) {

    Assert.notNull(candlesRequest, "Must inform a valid candle request.");
    validationUtil.throwIllegalArgumentExceptionIfNotValid(candlesRequest, "Candles request is invalid.");

    final var retrieveCandlesUri = buildRetrieveUri(candlesRequest);

    final var getCandleResponsePayloadToListCandleMapper = GetCandleResponsePayloadToListCandleMapper.builder()
      .dateTimeUtil(dateTimeUtil)
      .precision(candlesRequest.getResolution())
      .symbol(candlesRequest.getActive()
        .getSymbol())
      .build();

    final var getCandleResponsePayload = mbAuthenticatedWebClient.get()
      .uri(retrieveCandlesUri)
      .retrieve()
      .bodyToMono(GetCandleResponsePayload.class)
      .blockOptional()
      .orElseThrow(() -> {
        final var message = String.format("Could not retrieve candles for \"%s\" symbol.", candlesRequest.getActive());
        return new IllegalStateException(message);
      });

    final var candles = getCandleResponsePayloadToListCandleMapper.mapTo(getCandleResponsePayload);
    Collections.sort(candles);
    return candles;
  }

  private String buildRetrieveUri(CandlesRequest candlesRequest) {

    final var uriBuilder = new URIBuilder().setPathSegments("candles")
      .addParameter("symbol", candlesRequest.getActive()
        .getSymbol())
      .addParameter("resolution", candlesRequest.getResolution()
        .getValue());

    if (candlesRequest.getCountback() != null) {
      uriBuilder.addParameter("to", Integer.toString(candlesRequest.getToCount()));
      uriBuilder.addParameter("countback", Integer.toString(candlesRequest.getCountback()));
    } else {
      uriBuilder.addParameter("to", Long.toString(candlesRequest.getToTime()
        .toEpochSecond() - 1));
      uriBuilder.addParameter("from", Long.toString(candlesRequest.getFrom()
        .toEpochSecond()));
    }

    try {
      return uriBuilder
        .build()
        .toString();
    } catch (URISyntaxException exception) {
      throw new IllegalStateException("Exception thrown while building candles URI.", exception);
    }
  }
}
