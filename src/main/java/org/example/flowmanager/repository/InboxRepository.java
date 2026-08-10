package org.example.flowmanager.repository;

import org.example.flowmanager.model.entity.InboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InboxRepository extends JpaRepository<InboxMessage, String> {
    boolean existsByFileId(String fileId);
    InboxMessage findByFileId(String fileId);
    boolean existsByEventId(UUID eventId);

}
