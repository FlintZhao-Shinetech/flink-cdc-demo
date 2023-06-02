package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties(prefix = "flink.cdc.datasource")
@Data
public class FlinkConfig {
    private String host;
    private Integer port;
    private String databaseList;
    private String tableList;
    private String username;
    private String password;
}
