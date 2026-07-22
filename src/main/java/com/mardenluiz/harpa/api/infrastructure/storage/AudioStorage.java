package com.mardenluiz.harpa.api.infrastructure.storage;

import com.mardenluiz.harpa.api.web.dto.AudioDto;

import java.util.Optional;

public interface AudioStorage {

    Optional<AudioDto> getAudioByNumberFromStorage(int number);

}
