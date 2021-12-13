package com.github.marceloleite2604.cryptotrader.service;

import com.github.marceloleite2604.cryptotrader.dto.candle.GetCandleResponsePayload;
import com.github.marceloleite2604.cryptotrader.mapper.GetCandleResponsePayloadToListCandleMapper;
import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URISyntaxException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CandlesService {

  private final WebClient mbAuthenticatedWebClient;

  private final DateTimeUtil dateTimeUtil;


  public List<Candle> retrieve(CandlesRequest candlesRequest) {
    final var retrieveCandlesUri = buildRetrieveUri(candlesRequest);

    final var getCandleResponsePayloadToListCandleMapper = GetCandleResponsePayloadToListCandleMapper.builder()
      .dateTimeUtil(dateTimeUtil)
      .precision(candlesRequest.getResolution())
      .symbol(candlesRequest.getSymbol())
      .build();

    final var getCandleResponsePayload = mbAuthenticatedWebClient.get()
      .uri(retrieveCandlesUri)
      .retrieve()
      .bodyToMono(GetCandleResponsePayload.class)
      .blockOptional()
      .orElseThrow(() -> {
        final var message = String.format("Could not retrieve candles for \"%s\" symbol.", candlesRequest.getSymbol());
        return new IllegalStateException(message);
      });

    return getCandleResponsePayloadToListCandleMapper.mapTo(getCandleResponsePayload);


  }

  private String buildRetrieveUri(CandlesRequest candlesRequest) {
    Assert.notNull(candlesRequest, "Must inform a valid candle request.");
    Assert.isTrue(StringUtils.isNotBlank(candlesRequest.getSymbol()), "Must inform a symbol.");
    Assert.isTrue((
        (candlesRequest.getFrom() != null && candlesRequest.getToTime() != null) ||
          (candlesRequest.getCountback() != null && candlesRequest.getToCount() != null))
      , "Must inform either a \"from\" and \"toTime\" or \"countback\" and \"toCount\" end time.");

    final var uriBuilder = new URIBuilder().setPathSegments("candles")
      .addParameter("symbol", candlesRequest.getSymbol())
      .addParameter("resolution", candlesRequest.getResolution()
        .getValue());

    if (candlesRequest.getCountback() != null) {
      uriBuilder.addParameter("to", Integer.toString(candlesRequest.getToCount()));
      uriBuilder.addParameter("countback", Integer.toString(candlesRequest.getCountback()));
    } else {
      uriBuilder.addParameter("to", Long.toString(candlesRequest.getToTime()
        .toEpochSecond()));
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
