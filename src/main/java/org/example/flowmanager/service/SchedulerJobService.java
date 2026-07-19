package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.example.flowmanager.model.entity.OutboxTable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SchedulerJobService {
    private final OutboxManager outboxManager;

    @Transactional
    @Scheduled(fixedRateString = "${scheduler.fixed-rate}")
    @SchedulerLock(
            name = "${shedlock.name}",
            lockAtLeastFor = "${shedlock.lockAtLeastFor}",
            lockAtMostFor = "${shedlock.lockAtMostFor}"
    )
    public void jobScheduler() {
        List<OutboxTable> outboxTables = outboxManager.saveOutboxTable();

    }

}
