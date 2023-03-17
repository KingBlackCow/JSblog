package com.JSlog.JSblog.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Base64;

@Data
@Slf4j
@ConfigurationProperties(prefix = "jsblog")
public class AppConfig {

    private byte[] jwtKey;

    public void setJwtKey(String jwtKey) {
        this.jwtKey = Base64.getDecoder().decode(jwtKey);
    }

    public byte[] getJwtKey() {
        log.info("JWT= {}", jwtKey);
        return jwtKey;
    }
}
