package com.elmenus.droneia.infrastructure.aws.s3.service.impl;

import com.elmenus.droneia.domain.common.model.BasicResponse;
import com.elmenus.droneia.infrastructure.aws.s3.service.S3BucketService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.nio.ByteBuffer;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3BucketServiceImpl implements S3BucketService {

    @Value("${aws.s3.bucket}")
    private String bucketName;

    private final S3AsyncClient s3client;

    @Override
    public Mono<BasicResponse<String>> uploadFile(Mono<ByteBuffer> body) {
        var fileKey = UUID.randomUUID().toString();
        return (Mono.fromFuture(s3client.putObject(
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileKey)
                        .contentType(MediaType.APPLICATION_OCTET_STREAM_VALUE)
                        .build(),
                AsyncRequestBody.fromPublisher(body)
        )).map(response -> BasicResponse.<String>builder().data(fileKey).build()));
    }

    @Override
    public Mono<byte[]> getFile(String key) {
        return null;
    }
}
