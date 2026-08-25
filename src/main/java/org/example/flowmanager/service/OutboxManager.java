package org.example.flowmanager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.flowmanager.model.entity.OutboxTable;
import org.example.flowmanager.model.enums.FileRunStatus;
import org.example.flowmanager.repository.OutboxRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OutboxManager {
    private final OutboxRepository outboxRepository;

    public void save(OutboxTable outboxTable) {
        outboxRepository.save(outboxTable);
    }

    public List<OutboxTable> saveOutboxTable() {
        Pageable pageable = PageRequest.of(0, 100);
        return outboxRepository
                .findOutboxByFileRunStatus(FileRunStatus.NEW, pageable)
                .stream()
                .toList();
    }
}
