package org.example.flowmanager.repository;

import jakarta.persistence.LockModeType;
import lombok.NonNull;
import org.example.flowmanager.model.entity.OutboxTable;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository

public interface OutboxRepository extends CrudRepository<OutboxTable, UUID> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("""
    select o from OutboxTable o
    where o.conversionStatus = :conversionStatus
""")
    Page<@NonNull OutboxTable> findOutboxByFileRunStatus(ConversionStatus conversionStatus, Pageable pageable);

    OutboxTable findOutboxTableByUuidIs(UUID uuid);
}

