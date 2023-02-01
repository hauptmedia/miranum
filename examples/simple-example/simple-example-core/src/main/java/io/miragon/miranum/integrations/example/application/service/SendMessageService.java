package io.miragon.miranum.integrations.example.application.service;

import io.miragon.miranum.connect.message.application.port.in.CorrelateMessageCommand;
import io.miragon.miranum.connect.message.application.port.in.CorrelateMessageUseCase;
import io.miragon.miranum.connect.worker.domain.Worker;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageCommand;
import io.miragon.miranum.integrations.example.application.port.in.SendMessageUseCase;
import io.miragon.miranum.integrations.example.domain.Answer;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.util.Map;

@Log
@RequiredArgsConstructor
public class SendMessageService implements SendMessageUseCase {

    private final CorrelateMessageUseCase correlateMessageUseCase;

    @Override
    @Worker(type = "sendMessage")
    public Answer sendMessage(final SendMessageCommand message) {
        log.info("Received message: " + message);
        correlateMessageUseCase.correlateMessage(new CorrelateMessageCommand(message.getName(), message.getKey(), Map.of()));
        return new Answer("answer to: " + message.getName());
    }
}
