package com.mardenluiz.harpa.api.dto.mapstruct;

import com.mardenluiz.harpa.api.domain.HymnVerse;
import com.mardenluiz.harpa.api.dto.VerseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VerseMapper {

    VerseDto toVerseDto(HymnVerse hymnVerse);

    @Mapping(target = "hymn", ignore = true)
    @Mapping(target = "id", ignore = true)
    HymnVerse toVerse(VerseDto verseDto);
}
