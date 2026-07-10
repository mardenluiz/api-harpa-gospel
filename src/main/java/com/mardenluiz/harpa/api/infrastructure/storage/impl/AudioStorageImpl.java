package com.mardenluiz.harpa.api.infrastructure.storage.impl;


import com.mardenluiz.harpa.api.dto.AudioResponse;
import com.mardenluiz.harpa.api.infrastructure.storage.AudioStorage;
import jakarta.persistence.EntityNotFoundException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.helpers.DefaultHandler;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.util.Optional;

@Service
public class AudioStorageImpl implements AudioStorage {

    @Autowired
    private S3Client s3Client;

    @Value("${cloudflare.r2.bucket}")
    private String bucket;

    @Value("${cloudflare.r2.public-url}")
    private String publicUrl;


    @Override
    public Optional<AudioResponse> getAudioByNumberFromStorage(int hymnNumber) {

        String fileName = String.format("%03d.mp3", hymnNumber);
        String key = "hymns/" + fileName;

        try {

            HeadObjectResponse object =
                    s3Client.headObject(HeadObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .build());

            double sizeMb = object.contentLength() / 1024d / 1024d;

            return Optional.of(new AudioResponse(
                    publicUrl + "/" + key,
                    Math.round(sizeMb * 100.0) / 100.0,
                    getDuration(key)
            ));

        } catch (NoSuchKeyException e) {
            throw new EntityNotFoundException(
                    "Áudio do hino " + hymnNumber + " não encontrado."
            );
        } catch (S3Exception e) {

            if (e.statusCode() == 404) {
                throw new EntityNotFoundException(
                        "Áudio do hino " + hymnNumber + " não encontrado."
                );
            }

            throw e;
        }
    }

    private long getDuration(String key) {

        System.out.println(key);

        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        try (InputStream input = s3Client.getObject(request)) {

            Metadata metadata = new Metadata();
            ParseContext context = new ParseContext();
            AutoDetectParser parser = new AutoDetectParser();
            parser.parse(input, new DefaultHandler(), metadata, context);

            String duration = metadata.get("xmpDM:duration");

            if (duration == null) {
                return 0L;
            }

            return Math.round(Double.parseDouble(duration));

        } catch (Exception e) {
            throw new RuntimeException("Erro ao obter duração do áudio", e);
        }
    }

}