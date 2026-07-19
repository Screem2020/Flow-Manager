package org.example.flowmanager.repository;

import org.example.flowmanager.model.entity.InboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InboxRepository extends JpaRepository<InboxMessage, UUID> {
    boolean existById(UUID eventId);
    InboxMessage findInboxMessageByFileId(UUID eventId);
}
