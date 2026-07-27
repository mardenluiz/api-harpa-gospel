package com.mardenluiz.harpa.api.infrastructure.cache.audio;

import com.mardenluiz.harpa.api.web.dto.AudioDto;

import java.util.Optional;

public interface AudioCacheRepository {

    Optional<AudioDto> findByAudioNumberCache(int number);

    void save(AudioDto audio, int number);

    void clean();
}
