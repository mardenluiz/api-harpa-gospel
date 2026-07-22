package com.mardenluiz.harpa.api.web.mapstruct;

import com.mardenluiz.harpa.api.domain.model.Hymn;
import com.mardenluiz.harpa.api.web.dto.HymnDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HymnMapper {


    @Mapping(target = "id", ignore = true)
    Hymn toHymnEntity(HymnDto dto);

    HymnDto toHymnDto(Hymn hymn);
}
