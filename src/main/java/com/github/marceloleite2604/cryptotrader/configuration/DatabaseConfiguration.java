package com.github.marceloleite2604.cryptotrader.configuration;

import com.github.marceloleite2604.cryptotrader.properties.DatabaseProperties;
import lombok.SneakyThrows;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

  @Bean(BeanNames.DATA_SOURCE)
  public DataSource createDataSource(DatabaseProperties databaseProperties) {

    final var basicDataSource = new BasicDataSource();
    basicDataSource.setDriverClassName(databaseProperties.getDriverClassName());
    basicDataSource.setUrl(elaborateUri(databaseProperties));
    basicDataSource.setUsername(databaseProperties.getUsername());

    basicDataSource.setPassword(databaseProperties.getPassword());

    basicDataSource.setMaxIdle(databaseProperties.getConnections());
    basicDataSource.setMaxTotal(databaseProperties.getConnections());

    return basicDataSource;
  }

  @SneakyThrows
  private String elaborateUri(DatabaseProperties databaseProperties) {
    final var uriBuilder = new URIBuilder(databaseProperties.getUrl());

    if (MapUtils.isNotEmpty(databaseProperties.getOtherConnectionProperties())) {
      final var parameters = databaseProperties.getOtherConnectionProperties()
        .entrySet()
        .stream()
        .map(entry -> (NameValuePair) new BasicNameValuePair(entry.getKey(), entry.getValue()))
        .toList();
      uriBuilder.addParameters(parameters);
    }

    return uriBuilder.toString();
  }
}
