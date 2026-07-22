package com.mardenluiz.harpa.api.domain.service;

import com.mardenluiz.harpa.api.domain.model.Audio;
import com.mardenluiz.harpa.api.web.dto.AudioDto;
import com.mardenluiz.harpa.api.web.dto.HymnDto;
import com.mardenluiz.harpa.api.domain.exception.ResourceNotFoundException;
import com.mardenluiz.harpa.api.web.mapstruct.AudioMapper;
import com.mardenluiz.harpa.api.infrastructure.storage.impl.AudioStorageImpl;
import com.mardenluiz.harpa.api.domain.repository.AudioRepository;

import com.mardenluiz.harpa.api.web.mapstruct.HymnMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AudioService {


    private final AudioMapper mapper;
    private final HymnMapper hymnMapper;
    private final AudioRepository audioRepository;
    private final AudioStorageImpl audioStorage;
    private final HymnService hymnService;


    public AudioDto findAudioByNumber(int number) {
        return audioRepository.findByHymn_Number(number)
                .map(mapper::toAudioResponse)
                .orElseGet(() -> loadAndPersistAudio(number));
    }

    private AudioDto loadAndPersistAudio(int number) {

        AudioDto response = storageLoadAudio(number);
        HymnDto hymn = hymnService.findHymnByNumber(number);
        Audio audio = mapper.toAudio(response);
        audio.setHymn(hymnMapper.toHymnEntity(hymn));

        audioRepository.save(audio);

        return response;
    }

    private AudioDto storageLoadAudio(int number) {
        return audioStorage.getAudioByNumberFromStorage(number)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(
                        "Áudio de numero %d não encontrado!", number)));
    }

}
