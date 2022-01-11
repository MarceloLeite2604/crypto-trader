package com.github.marceloleite2604.cryptotrader;

import com.github.marceloleite2604.cryptotrader.service.AccountService;
import com.github.marceloleite2604.cryptotrader.service.analyser.AnalyserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScheduledTaskAnalyser {

  private final AccountService accountService;

  private final AnalyserService analyserService;

  //  @Scheduled(cron = "0 * * ? * ?")
  public void run() {

    final var account = accountService.retrieve();

    analyserService.analyse(account);

  }
}
