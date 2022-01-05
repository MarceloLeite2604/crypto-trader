package com.github.marceloleite2604.cryptotrader;

import com.github.marceloleite2604.cryptotrader.model.Active;
import com.github.marceloleite2604.cryptotrader.service.analyser.AnalyserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskAnalyser {

  private final List<Active> ACTIVES_TO_CHECK = List.of(Active.ETHEREUM, Active.BITCOIN, Active.LITECOIN);

  private final AnalyserService analyserService;

//  @Scheduled(cron = "0 * * ? * ?")
  public void run() {
    ACTIVES_TO_CHECK.forEach(analyserService::analyse);
  }
}
