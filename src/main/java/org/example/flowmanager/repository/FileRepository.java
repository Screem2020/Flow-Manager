package org.example.flowmanager.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface FileRepository extends CrudRepository<String, UUID> {
}
