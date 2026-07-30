package org.example.flowmanager.repository;

import org.example.flowmanager.model.entity.InboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InboxRepository extends JpaRepository<InboxMessage, String> {
    boolean existsByFileId(String fileUuid);
    InboxMessage findByFileId(String fileId);
}
