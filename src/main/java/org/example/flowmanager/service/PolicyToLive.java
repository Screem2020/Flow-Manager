package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.model.entity.OutboxTable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;

@RequiredArgsConstructor
@Service
public class PolicyToLive {
    @Value("${outbox.check-lock.max-attempts}")
    private Integer maxAttempts;
    @Value("${outbox.check-lock.timeout}")
    private Duration timeout;

    private boolean checkAttempts(OutboxTable outboxTable) {
        return maxAttempts <= outboxTable.getAttempts();
    }

    private boolean checkTimeLock(OutboxTable outboxTable) {
        Duration between = Duration.between(outboxTable.getTimeToLive(), Instant.now());
        return between.compareTo(timeout) >= 0;
    }
    //проверка времени и количество попыток на выход из доупстимых значений на каждой итерации
    public boolean checkPolicyTimeToLive(OutboxTable outboxTable) {
        return checkAttempts(outboxTable) && checkTimeLock(outboxTable);
    }
    //установка начальных значений при первой итерации
    public void processTimeToLive(OutboxTable outboxTable) {
        if (outboxTable.getTimeToLive() == null) {
            outboxTable.setTimeToLive(Instant.now());
        }
        outboxTable.setAttempts(outboxTable.getAttempts() + 1);
    }
}

