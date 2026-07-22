package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.example.flowmanager.model.entity.OutboxTable;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.model.enums.FileRunStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class SchedulerJobService {
    private final OutboxManager outboxManager;
    private final PolicyToLive policyToLive;
    private final Dispatcher dispatcher;


    @Transactional
    @Scheduled(fixedRateString = "${scheduler.fixed-rate}")
    @SchedulerLock(
            name = "TaskSchedulerOutbox",
            lockAtLeastFor = "PT1M",
            lockAtMostFor = "PT5M"
    )
    public void jobScheduler() {
        List<OutboxTable> outboxTables = outboxManager.saveOutboxTable();
        if (outboxTables.isEmpty()) {
            log.info("No event found");
            return;
        }
        for (OutboxTable outboxTable : outboxTables) {
            // запуск счетчиков
            policyToLive.processTimeToLive(outboxTable);
            // проверка на не соблюдение времени и попыток
            if (policyToLive.checkPolicyTimeToLive(outboxTable)) {
                outboxTable.setConversionStatus(ConversionStatus.DLT_FILE);
            } else {
                dispatcher.dispatcher(outboxTable);
            }
            outboxTable.setFileRunStatus(FileRunStatus.NEW);
        }
    }
}
