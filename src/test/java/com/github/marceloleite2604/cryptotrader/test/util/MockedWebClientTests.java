package com.github.marceloleite2604.cryptotrader.test.util;

import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Spy;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class MockedWebClientTests {

  protected static MockWebServer mockWebServer;

  protected static String baseUrl;

  @Spy
  private WebClient webClient = WebClient.builder()
    .baseUrl(baseUrl)
    .build();

  @BeforeAll
  static void setUpAll() throws IOException {
    mockWebServer = new MockWebServer();
    mockWebServer.start();

    baseUrl = String.format("http://localhost:%d", mockWebServer.getPort());
  }

  @AfterAll
  static void tearDownAll() throws IOException {
    mockWebServer.shutdown();
  }

//  @BeforeEach
//  void setUp() {
//    webClient = WebClient.builder()
//      .baseUrl(baseUrl)
//      .build();
//  }
}
