package com.github.marceloleite2604.cryptotrader.service.candle;

import com.github.marceloleite2604.cryptotrader.model.candles.Candle;
import com.github.marceloleite2604.cryptotrader.model.candles.CandlesRequest;
import com.github.marceloleite2604.cryptotrader.service.mercadobitcoin.MercadoBitcoinService;
import com.github.marceloleite2604.cryptotrader.test.util.fixture.model.candles.CandlesRequestFixture;
import com.github.marceloleite2604.cryptotrader.util.DateTimeUtil;
import com.github.marceloleite2604.cryptotrader.util.ValidationUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
class CandleServiceTest {

  @InjectMocks
  private CandleService candleService;

  @Mock
  private MercadoBitcoinService mercadoBitcoinService;

  @Mock
  private ValidationUtil validationUtil;

  @Mock
  private DateTimeUtil dateTimeUtil;

  @Mock
  private CandleComparisonService candleComparisonService;

  @Test
  void shouldRetrieveCandlesFromMb() {

    final var candlesRequest = CandlesRequestFixture.createWithTime();

    final var actual = candleService.retrieveCandles(candlesRequest);
  }

}