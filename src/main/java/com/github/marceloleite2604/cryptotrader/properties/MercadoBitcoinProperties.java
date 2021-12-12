package com.github.marceloleite2604.cryptotrader.properties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.net.URI;

@Setter
@Getter
@NoArgsConstructor(force = true)
@ConfigurationProperties(PropertiesPath.MERCADO_BITCOIN)
@Validated
@Component
public class MercadoBitcoinProperties {

    @NotBlank
    private String baseUri;

    @NotBlank
    private String clientId;

    @NotBlank
    private String clientSecret;
}
