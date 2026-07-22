package com.mardenluiz.harpa.api.infrastructure.storage.impl;


import com.mardenluiz.harpa.api.web.dto.AudioDto;
import com.mardenluiz.harpa.api.domain.model.Audio;
import com.mardenluiz.harpa.api.domain.model.Hymn;
import com.mardenluiz.harpa.api.domain.repository.AudioRepository;
import com.mardenluiz.harpa.api.domain.repository.HymnRepository;
import com.mardenluiz.harpa.api.infrastructure.storage.AudioStorage;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.xml.sax.helpers.DefaultHandler;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AudioStorageImpl implements AudioStorage {

    private final S3Client s3Client;
    private final HymnRepository hymnRepository;
    private final AudioRepository audioRepository;

    @Value("${cloudflare.r2.bucket}")
    private String bucket;

    @Value("${cloudflare.r2.public-url}")
    private String publicUrl;



    @Override
    public Optional<AudioDto> getAudioByNumberFromStorage(int hymnNumber) {

        String fileName = String.format("%03d.mp3", hymnNumber);
        String key = "hymns/" + fileName;

        try {
            HeadObjectResponse object = s3Client.headObject(HeadObjectRequest.builder()
                            .bucket(bucket)
                            .key(key)
                            .build());

            double sizeMb = object.contentLength() / 1024d / 1024d;

            return Optional.of(new AudioDto(
                    publicUrl + "/" + key,
                    Math.round(sizeMb * 100.0) / 100,
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


    public void importAudios() {

        ListObjectsV2Request request = ListObjectsV2Request.builder()
                        .bucket(bucket)
                        .prefix("hymns/")
                        .build();

        ListObjectsV2Response response = s3Client.listObjectsV2(request);

        for (S3Object object : response.contents()) {

            if (!object.key().endsWith(".mp3")) {
                continue;
            }

            String fileName = Paths.get(object.key())
                            .getFileName()
                            .toString();

            int hymnNumber = Integer.parseInt(fileName.replace(".mp3", ""));

            if (audioRepository.existsByHymn_Number(hymnNumber)) {
                continue;
            }

            Hymn hymn = hymnRepository.findByNumber(hymnNumber)
                    .orElse(null);

            if (hymn == null) {
                continue;
            }

            Audio audio = new Audio();
            audio.setHymn(hymn);
            audio.setUrl(publicUrl + "/hymns/" + fileName);
            audio.setSize(object.size());
            audio.setDuration(getDuration(object.key()));

            audioRepository.save(audio);

        }

    }

    private long getDuration(String key) {

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