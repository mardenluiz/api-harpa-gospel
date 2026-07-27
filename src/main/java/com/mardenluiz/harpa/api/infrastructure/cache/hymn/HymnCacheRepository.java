package com.mardenluiz.harpa.api.infrastructure.cache.hymn;

import com.mardenluiz.harpa.api.web.dto.HymnDto;

import java.util.Optional;

public interface HymnCacheRepository {

    Optional<HymnDto> findHymnByNumberCache(int number);

    void save(HymnDto hymn);

    void clean();

}