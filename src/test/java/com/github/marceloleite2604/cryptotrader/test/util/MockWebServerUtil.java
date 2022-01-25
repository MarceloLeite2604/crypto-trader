package com.github.marceloleite2604.cryptotrader.test.util;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.MimeTypeUtils;

import java.net.URI;
import java.nio.charset.StandardCharsets;

public class MockWebServerUtil {

  public static Stub using(MockWebServer mockWebServer) {
    return new Stub(new Context(mockWebServer));
  }

  @RequiredArgsConstructor
  private static class Context {
    private final MockWebServer mockWebServer;

    private HttpMethod httpMethod;

    private URI uri;

    private MediaType mediaType;

    private String requestBody;

    private HttpStatus httpStatus;

    private Object response;

    private void buildDispatcher() {
      Dispatcher dispatcher = new Dispatcher() {
        @SneakyThrows
        @NonNull
        @Override
        public MockResponse dispatch(@NonNull RecordedRequest request) {
          String uriString = uri.getPath();

          if (!StringUtils.isEmpty(uri.getQuery())) {
            uriString += "?" + uri.getQuery();
          }

          if (!uriString.equals(request.getPath())) {
            return new MockResponse().setResponseCode(HttpStatus.NOT_FOUND.value());
          }

          if (!httpMethod.name()
            .equals(request.getMethod())) {
            return new MockResponse().setResponseCode(HttpStatus.NOT_FOUND.value());
          }

          if (mediaType != null && !mediaType.toString()
            .equals(request.getHeader(HttpHeaders.CONTENT_TYPE))) {
            return new MockResponse().setResponseCode(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value());
          }

          if (StringUtils.isNotEmpty(requestBody) && !requestBody.equals(request.getBody()
            .readString(StandardCharsets.UTF_8))) {
            return new MockResponse().setResponseCode(HttpStatus.BAD_REQUEST.value());
          }

          return new MockResponse().setResponseCode(httpStatus.value())
            .setBody(ObjectMapperUtil.getObjectMapper()
              .writeValueAsString(response))
            .setHeader(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE);
        }
      };

      mockWebServer.setDispatcher(dispatcher);
    }
  }

  @RequiredArgsConstructor
  public static class Stub {
    private final Context context;

    @SneakyThrows
    public StubRequest when(HttpMethod httpMethod, URI uri, MediaType mediaType, Object body) {
      context.httpMethod = httpMethod;
      context.uri = uri;
      context.mediaType = mediaType;

      if (body != null) {
        context.requestBody = ObjectMapperUtil.getObjectMapper()
          .writeValueAsString(body);
      }
      return new StubRequest(context);
    }

    @SneakyThrows
    public StubRequest when(HttpMethod httpMethod, URI uri, MediaType mediaType) {
      return when(httpMethod, uri, mediaType, null);
    }

    @SneakyThrows
    public StubRequest when(HttpMethod httpMethod, URI uri) {
      return when(httpMethod, uri, null, null);
    }

    @SneakyThrows
    public StubRequest when(HttpMethod httpMethod, String path) {
      final var uri = String.format("http://localhost:%d/%s", context.mockWebServer.getPort(), path);
      return when(httpMethod, new URI(uri), null, null);
    }
  }

  @RequiredArgsConstructor
  public static class StubRequest {

    private final Context context;

    public void thenReturn(HttpStatus httpStatus, Object response) {
      context.httpStatus = httpStatus;
      context.response = response;

      context.buildDispatcher();
    }
  }
}
