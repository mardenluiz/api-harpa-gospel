package com.mardenluiz.harpa.api.infrastructure.storage;

import com.mardenluiz.harpa.api.api.dto.AudioDto;

import java.util.Optional;

public interface AudioStorage {

    Optional<AudioDto> getAudioByNumberFromStorage(int number);

}
