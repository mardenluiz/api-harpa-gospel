package com.mardenluiz.harpa.api.dto;

import com.mardenluiz.harpa.api.domain.Hymn;

public record AudioResponse(
        String url,
        double size,
        double duration
) {

}