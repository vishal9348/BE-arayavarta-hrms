package com.aryavarta.identity.security.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@Getter
@Setter
@ConfigurationProperties(prefix = "aryavarta.security.jwt")
public class JwtProperties {


    private String secret;

    private Duration accessTokenExpiration = Duration.ofMinutes(15);

    private Duration refreshTokenExpiration = Duration.ofDays(30);

    private String issuer = "aryavarta";

    private String audience = "aryavarta-api";
}