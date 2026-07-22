package org.example.flowmanager.controller;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.model.dto.ReplyToUser;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.service.FlowManager;
import org.example.flowmanager.service.LoadFileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/load")
public class LoadFileController {

    private  final FlowManager flowManager;
    private final LoadFileService loadFileService;

    @PostMapping
    public ResponseEntity<ReplyToUser> uploadFile(@RequestParam("file") MultipartFile file) {
        System.out.println("CONTROLLER START");
        return ResponseEntity.ok(flowManager.processUploadFile(file));
    }

    @GetMapping("/{fileId}/status")
    public ResponseEntity<ConversionStatus> getStatus(@PathVariable UUID fileId) {
        return ResponseEntity.ok(loadFileService.getStatus(fileId));
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<byte[]> getFile(@PathVariable UUID fileId) {
        return ResponseEntity.ok(loadFileService.getFile(fileId));
    }
}
