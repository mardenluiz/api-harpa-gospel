package com.mardenluiz.harpa.api.api.dto;

import java.util.List;

public record PageResponse<T>(

        List<HymnDto> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last

) {
}
