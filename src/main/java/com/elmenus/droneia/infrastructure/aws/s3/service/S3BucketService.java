package com.elmenus.droneia.infrastructure.aws.s3.service;

import com.elmenus.droneia.domain.common.BasicResponse;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Mono;

import java.nio.ByteBuffer;

public interface S3BucketService {
    Mono<BasicResponse<String>> uploadFile(Mono<ByteBuffer> filePartMono);

    Mono<byte[]> getFile(@NotNull String key);
}
