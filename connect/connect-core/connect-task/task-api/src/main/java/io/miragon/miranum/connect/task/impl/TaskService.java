package io.miragon.miranum.connect.task.impl;

import io.miragon.miranum.connect.task.api.CompleteTaskCommand;
import io.miragon.miranum.connect.task.api.TaskApi;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class TaskService implements TaskApi {

    private final CompleteTaskPort deliverMessagePort;

    @Override
    public void correlateMessage(final CompleteTaskCommand command) {
        this.deliverMessagePort.deliverMessage(command);
    }
}
