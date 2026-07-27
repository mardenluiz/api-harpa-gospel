package com.mardenluiz.harpa.api.infrastructure.cache.hymn;

import com.mardenluiz.harpa.api.web.dto.HymnDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HymnCacheService implements HymnCacheRepository {

    private static final String PREFIX = "hymn::";
    private static final Duration TTL = Duration.ofHours(24);
    private final RedisTemplate<String, HymnDto> redisTemplate;

    @Override
    public Optional<HymnDto> findHymnByNumberCache(int number) {
        HymnDto hymn = redisTemplate.opsForValue().get(PREFIX + number);
        return Optional.ofNullable(hymn);
    }

    @Override
    public void save(HymnDto hymn) {
        redisTemplate.opsForValue().set(PREFIX + hymn.getNumber(), hymn, TTL);
    }


    @Override
    public void clean() {
        Set<String> keys = redisTemplate.keys(PREFIX + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
