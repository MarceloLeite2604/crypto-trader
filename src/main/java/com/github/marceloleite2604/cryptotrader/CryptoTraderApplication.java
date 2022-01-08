package com.github.marceloleite2604.cryptotrader;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@SpringBootApplication
@EnableScheduling
@Slf4j
public class CryptoTraderApplication {

  public static void main(String[] args) {
    SpringApplication.run(CryptoTraderApplication.class, args);
  }

}

