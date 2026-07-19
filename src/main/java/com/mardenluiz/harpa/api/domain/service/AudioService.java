package com.mardenluiz.harpa.api.domain.service;

import com.mardenluiz.harpa.api.domain.model.Audio;
import com.mardenluiz.harpa.api.domain.model.Hymn;
import com.mardenluiz.harpa.api.api.dto.AudioDto;
import com.mardenluiz.harpa.api.api.mapstruct.AudioMapper;
import com.mardenluiz.harpa.api.infrastructure.storage.impl.AudioStorageImpl;
import com.mardenluiz.harpa.api.domain.repository.AudioRepository;
import com.mardenluiz.harpa.api.domain.repository.HymnRepository;

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
    public AudioDto findByNumber(int number) {

        return audioRepository.findByHymn_Number(number)
                .map(mapper::toAudioResponse)
                .orElseGet(() -> loadAndPersistAudio(number));
    }

    private AudioDto loadAndPersistAudio(int number) {

        AudioDto response = storageLoadAudio(number);

        Hymn hymn = findHymn(number);
        Audio audio = mapper.toAudio(response);
        audio.setHymn(hymn);

        audioRepository.save(audio);

        return response;
    }

    private AudioDto storageLoadAudio(int number) {
        return audioStorage.getAudioByNumberFromStorage(number)
                .orElseThrow(() -> new ObjectNotFoundException(number, "Áudio não encontrado."));
    }

    public Hymn findHymn(int number) {
        return hymnRepository.findByNumber(number)
                .orElseThrow(() -> new ObjectNotFoundException(number, "Hino não encontrado!"));
    }

}
