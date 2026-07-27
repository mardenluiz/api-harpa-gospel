package com.mardenluiz.harpa.api.infrastructure.cache.audio;

import com.mardenluiz.harpa.api.web.dto.AudioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AudioCacheService implements AudioCacheRepository {

    private static final String PREFIX = "audio::";
    private static final Duration TTL = Duration.ofHours(24);
    private final RedisTemplate<String, AudioDto> redisTemplate;

    @Override
    public Optional<AudioDto> findByAudioNumberCache(int number) {
        AudioDto audio = redisTemplate.opsForValue().get(PREFIX + number);
        return Optional.ofNullable(audio);
    }

    @Override
    public void save(AudioDto audio, int number) {
        redisTemplate.opsForValue().set(PREFIX + number, audio, TTL);
    }


    @Override
    public void clean() {
        Set<String> keys = redisTemplate.keys(PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
