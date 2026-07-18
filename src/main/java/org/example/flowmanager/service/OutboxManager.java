package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.model.entity.OutboxTable;
import org.example.flowmanager.model.enums.FileRunStatus;
import org.example.flowmanager.repository.OutboxRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OutboxManager {
    private final OutboxRepository outboxRepository;

    public void save(OutboxTable outboxTable) {
        outboxRepository.save(outboxTable);
    }

    public List<OutboxTable> saveOutboxTable() {
        Pageable pageable = PageRequest.of(0, 100);
        Page<OutboxTable> outboxByFileRunStatus = outboxRepository.findOutboxByFileRunStatus(FileRunStatus.IN_PROGRESS, pageable);
        return outboxByFileRunStatus.getContent();
    }
}
