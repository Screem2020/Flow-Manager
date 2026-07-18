package org.example.flowmanager.controller;

import lombok.RequiredArgsConstructor;
import org.example.flowmanager.model.enums.ConversionStatus;
import org.example.flowmanager.service.FlowManager;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/download")
public class LoadFileController {

    private  final FlowManager flowManager;

    @PostMapping
    public ResponseEntity<ConversionStatus> uploadFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity
                .ok(flowManager.process(file));
    }
    //TODO: еще 2 клонтроллера
}
