package org.example.flowmanager.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.flowmanager.model.dto.ReplyToUserDto;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.service.LoadFileService;
import org.example.flowmanager.service.SubscriptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/flow-manager")
public class LoadFileController {

    private final LoadFileService loadFileService;

    @PostMapping("/upload")
    public ResponseEntity<ReplyToUserDto> uploadFile(@RequestParam("file") MultipartFile file,
                                                     @RequestHeader("X-User-Login") String login) {
        return ResponseEntity.ok(loadFileService.processUploadFile(file, login));
    }

    @GetMapping("/{fileId}/status")
    public ResponseEntity<ConversionStatus> getStatus(@PathVariable UUID fileId) {
        return ResponseEntity.ok(loadFileService.getStatus(fileId));
    }

    @GetMapping("/{fileId}/download")
    public ResponseEntity<InputStream> getFile(@PathVariable UUID fileId) {
        return ResponseEntity.ok(loadFileService.getFile(fileId));
    }

//    @GetMapping("/subscription")
//    public void getLogin(@RequestHeader("X-User-Login") String login) {
//        ResponseEntity.ok(subscriptionService.checkSubscriptionLogin(login));
//    }
}
