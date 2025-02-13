package io.miragon.miranum.connect.c7.utils;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda7UtilsAutoConfiguration {

    @Bean
    public Camunda7BaseVariableValueMapper camunda7BaseVariableMapper() {
        return new Camunda7BaseVariableValueMapper();
    }
}
