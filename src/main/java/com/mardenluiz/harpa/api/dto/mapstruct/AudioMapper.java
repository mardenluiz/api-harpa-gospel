package com.mardenluiz.harpa.api.dto.mapstruct;

import com.mardenluiz.harpa.api.domain.Audio;
import com.mardenluiz.harpa.api.dto.AudioDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface AudioMapper {


    AudioDto toAudioResponse(Audio audio);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "hymn", ignore = true)
    Audio toAudio(AudioDto audioDto);
}
