package io.miragon.miranum.connect.adapter.in.c8.worker;

import io.miragon.miranum.connect.adapter.in.c7.worker.Camunda7Mapper;
import io.miragon.miranum.connect.adapter.in.c7.worker.Camunda7WorkerAdapter;
import io.miragon.miranum.connect.worker.impl.MethodExecutor;
import io.miragon.miranum.connect.worker.impl.WorkerInfo;
import org.camunda.bpm.client.ExternalTaskClient;
import org.camunda.bpm.client.task.ExternalTask;
import org.camunda.bpm.client.task.ExternalTaskService;
import org.camunda.bpm.client.topic.TopicSubscription;
import org.camunda.bpm.client.topic.TopicSubscriptionBuilder;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

public class Camunda7AdapterTest {

    private final ExternalTaskClient externalTaskClient =
            Mockito.mock(ExternalTaskClient.class);

    private final Camunda7Mapper camunda7Mapper =
            Mockito.mock(Camunda7Mapper.class);

    private final MethodExecutor methodExecutor =
            Mockito.mock(MethodExecutor.class);

    private final Camunda7WorkerAdapter adapter =
            new Camunda7WorkerAdapter(this.externalTaskClient, this.camunda7Mapper, this.methodExecutor);

    @Test
    void givenOneUseCase_thenExternalTaskClientSubscribesOnce() {
        final WorkerInfo defaultWorker = this.givenDefaultWorker("defaultWorker", 100L);
        final TopicSubscriptionBuilder builder = this.givenTopicSubscriptionBuilder();

        given(this.externalTaskClient.subscribe("defaultWorker")).willReturn(builder);

        this.adapter.bind(defaultWorker);

        then(this.externalTaskClient).should().subscribe("defaultWorker");
        then(this.externalTaskClient).shouldHaveNoMoreInteractions();

        then(builder).should().open();
        then(builder).should().handler(any());
        then(builder).should().lockDuration(100L);
        then(builder).shouldHaveNoMoreInteractions();
    }


    @Test
    void givenDefaultUseCaseAndSuccessfulTask_thenEverythingGetsExecuted() {
        final ExternalTask externalTask = this.givenDefaultTask();
        final ExternalTaskService service = this.givenExternalTaskService();
        final WorkerInfo defaultWorker = this.givenDefaultWorker("defaultWorker", 100L);
        final Map<String, Object> result = Map.of("value", "test");
        given(this.methodExecutor.execute(any())).willReturn(result);
        given(this.camunda7Mapper.mapOutput(any())).willReturn(result);

        this.adapter.execute(externalTask, service, defaultWorker);

        then(service).should().complete(externalTask, null, result);
    }

    private WorkerInfo givenDefaultWorker(final String type, final Long lockDuration) {
        final WorkerInfo workerInfo = Mockito.mock(WorkerInfo.class);
        given(workerInfo.getType()).willReturn(type);
        given(workerInfo.getTimeout()).willReturn(lockDuration);
        return workerInfo;
    }

    private ExternalTask givenDefaultTask() {
        return Mockito.mock(ExternalTask.class);
    }

    private ExternalTaskService givenExternalTaskService() {
        return Mockito.mock(ExternalTaskService.class);
    }

    private TopicSubscriptionBuilder givenTopicSubscriptionBuilder() {
        final TopicSubscriptionBuilder builder = Mockito.mock(TopicSubscriptionBuilder.class);
        given(builder.handler(any())).willReturn(builder);
        given(builder.lockDuration(anyLong())).willReturn(builder);
        given(builder.open()).willReturn(Mockito.mock(TopicSubscription.class));
        return builder;
    }

}
