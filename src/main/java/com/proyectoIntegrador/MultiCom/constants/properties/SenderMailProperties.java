package com.proyectoIntegrador.MultiCom.constants.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

@Data
@Service
@ConfigurationProperties(prefix = "sender-mail")
public
class SenderMailProperties {
    private String email;
    private String password;
    private String host;
    private String starttls;
    private String port;
    private String auth;
}
