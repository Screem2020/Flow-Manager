package org.example.flowmanager.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.model.enums.FileRunStatus;
import org.hibernate.annotations.ColumnTransformer;

import java.time.Instant;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "outbox_table")
public class OutboxTable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(columnDefinition = "jsonb")
    @ColumnTransformer(write = "?::jsonb")
    private String payload;
    private Instant timeToLive;
    private Integer attempts;
    @Enumerated(EnumType.STRING)
    private ConversionStatus conversionStatus;
    @Enumerated(EnumType.STRING)
    private FileRunStatus fileRunStatus;

}
