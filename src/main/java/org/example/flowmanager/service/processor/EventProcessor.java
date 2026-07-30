package org.example.flowmanager.service.processor;

import org.example.flowmanager.model.entity.OutboxTable;

public interface EventProcessor {
     void process(OutboxTable outboxTable);
}
