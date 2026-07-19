package org.example.flowmanager.service;

import org.example.flowmanager.model.entity.OutboxTable;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.service.processor.DltEventProcessor;
import org.example.flowmanager.service.processor.EventProcessor;
import org.example.flowmanager.service.processor.FailedProcessor;
import org.example.flowmanager.service.processor.UpdateProcessor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class Dispatcher {
    private final Map<ConversionStatus, EventProcessor> changeProcessors;

    public Dispatcher(UpdateProcessor updateProcessor,
                      FailedProcessor failedProcessor,
                      DltEventProcessor dltEventProcessor) {
        this.changeProcessors = Map.of(
                ConversionStatus.PROGRESS_FILE, updateProcessor,
                ConversionStatus.FAILED_FILE, failedProcessor,
                ConversionStatus.DLT_FILE, dltEventProcessor);
    }

    public void dispatcher(OutboxTable outboxTable) {
        changeProcessors.get(outboxTable.getConversionStatus()).process(outboxTable);
    }
}
