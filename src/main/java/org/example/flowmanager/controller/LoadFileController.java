package org.example.flowmanager.controller;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.model.enums.FileRunStatus;
import org.example.flowmanager.service.RequestToUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/download")
public class LoadFileController {

    private  final RequestToUser requestToUser;

    @PostMapping
    public ResponseEntity<FileRunStatus> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam UUID uuid) {
        return ResponseEntity
                .ok(requestToUser.uploadFile(uuid, file).getFileRunStatus());
    }
    //TODO: еще 2 клонтроллера
}
