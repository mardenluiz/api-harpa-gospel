package com.mardenluiz.harpa.api.dto.mapstruct;

import com.mardenluiz.harpa.api.domain.Hymn;
import com.mardenluiz.harpa.api.dto.HymnDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HymnMapper {


    @Mapping(target = "id", ignore = true)
    Hymn toHymnEntity(HymnDto dto);

    HymnDto toHymnDto(Hymn hymn);
}
