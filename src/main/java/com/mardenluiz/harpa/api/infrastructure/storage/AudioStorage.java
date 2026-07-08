package com.mardenluiz.harpa.api.infrastructure.storage;

import com.mardenluiz.harpa.api.dto.AudioResponse;

import java.util.Optional;

public interface AudioStorage {

    Optional<AudioResponse> getAudioByNumberFromStorage(int number);

}
