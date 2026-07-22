package com.mardenluiz.harpa.api.web.dto;

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
