package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.example.flowmanager.model.entity.OutboxTable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final OutboxManager outboxManager;

    @Transactional
    @Scheduled(fixedRate = 1000)
    @SchedulerLock(
            name = "taskSchedulerOutbox",
            lockAtLeastFor = "PT5M",
            lockAtMostFor = "PT10M"
    )
    public void jobScheduler() {
        List<OutboxTable> outboxTables = outboxManager.saveOutboxTable();

    }

}
