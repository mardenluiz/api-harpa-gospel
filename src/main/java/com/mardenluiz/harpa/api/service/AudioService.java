package com.mardenluiz.harpa.api.service;

import com.mardenluiz.harpa.api.domain.Audio;
import com.mardenluiz.harpa.api.domain.Hymn;
import com.mardenluiz.harpa.api.dto.AudioResponse;
import com.mardenluiz.harpa.api.dto.mapstruct.AudioMapper;
import com.mardenluiz.harpa.api.infrastructure.storage.impl.AudioStorageImpl;
import com.mardenluiz.harpa.api.repository.AudioRepository;
import com.mardenluiz.harpa.api.repository.HymnRepository;

import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AudioService {


    private final AudioMapper mapper;
    private final AudioRepository audioRepository;
    private final HymnRepository hymnRepository;
    private final AudioStorageImpl audioStorage;


    @Transactional
    public AudioResponse findByNumber(int number) {

        return audioRepository.findByHymn_Number(number)
                .map(mapper::audioToAudioResponse)
                .orElseGet(() -> loadAndPersistAudio(number));
    }

    private AudioResponse loadAndPersistAudio(int number) {

        AudioResponse response = storageLoadAudio(number);

        Hymn hymn = findHymn(number);
        Audio audio = mapper.toAudio(response);
        audio.setHymn(hymn);

        audioRepository.save(audio);

        return response;
    }

    private AudioResponse storageLoadAudio(int number) {
        return audioStorage.getAudioByNumberFromStorage(number)
                .orElseThrow(() -> new ObjectNotFoundException(number, "Áudio não encontrado."));
    }

    public Hymn findHymn(int number) {
        return hymnRepository.findByNumber(number)
                .orElseThrow(() -> new ObjectNotFoundException(number, "Hino não encontrado!"));
    }

}
