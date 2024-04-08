package com.elmenus.droneia.application.s3.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

@RestController
@RequestMapping("/droneia/api/v1/s3")
@RequiredArgsConstructor
public class S3Controller {

    @PostMapping("/upload")
    public ResponseEntity uploadFile(@RequestPart("file") Mono<ByteBuffer> filePartMono) {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PostMapping("/download")
    public ResponseEntity downloadFile() {
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
