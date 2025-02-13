package io.miragon.miranum.connect.adapter.in.c7.worker;

import io.miragon.miranum.connect.worker.impl.MethodExecutor;
import org.camunda.bpm.client.ExternalTaskClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties
public class Camunda7WorkerAutoConfiguration {

    @Bean
    public Camunda7WorkerAdapter camunda7WorkerAdapter(final ExternalTaskClient externalTaskClient, final MethodExecutor methodExecutor) {
        return new Camunda7WorkerAdapter(externalTaskClient, new Camunda7Mapper(), methodExecutor);
    }

}
