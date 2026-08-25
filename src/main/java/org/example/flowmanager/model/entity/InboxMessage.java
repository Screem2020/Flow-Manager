package org.example.flowmanager.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Setter
@Getter
@Table(name = "inbox_message")
public class InboxMessage {
    @Id
    private UUID eventId;
    private String fileId;
    private String payload;
}
