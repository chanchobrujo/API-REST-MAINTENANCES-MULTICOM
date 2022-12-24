package com.proyectoIntegrador.MultiCom.constants.properties;

import lombok.Data;

import org.springframework.stereotype.Service;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@Service
@ConfigurationProperties(prefix = "token")
public
class TokenProperties {
    private String secret;
    private Integer expiration;
}
