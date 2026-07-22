package com.mardenluiz.harpa.api.service;

import com.mardenluiz.harpa.api.domain.exception.ResourceNotFoundException;
import com.mardenluiz.harpa.api.domain.model.Audio;
import com.mardenluiz.harpa.api.domain.model.Hymn;
import com.mardenluiz.harpa.api.domain.service.AudioService;
import com.mardenluiz.harpa.api.domain.service.HymnService;
import com.mardenluiz.harpa.api.web.dto.AudioDto;
import com.mardenluiz.harpa.api.infrastructure.storage.impl.AudioStorageImpl;
import com.mardenluiz.harpa.api.web.dto.HymnDto;
import com.mardenluiz.harpa.api.web.mapstruct.HymnMapper;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.mardenluiz.harpa.api.web.mapstruct.AudioMapper;

import com.mardenluiz.harpa.api.domain.repository.AudioRepository;
import com.mardenluiz.harpa.api.domain.repository.HymnRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class AudioServiceTest {

    @Mock
    private AudioMapper mapper;

    @Mock
    private HymnMapper hymnMapper;

    @Mock
    private AudioRepository audioRepository;

    @Mock
    private HymnRepository hymnRepository;

    @Mock
    private AudioStorageImpl audioStorage;

    @Mock
    private HymnService hymnService;

    @InjectMocks
    private AudioService service;

    @Test
    void shouldReturnAudioFromDatabase() {

        int number = 10;

        Audio audio = new Audio();
        AudioDto response = new AudioDto("url", 100, 120);

        given(audioRepository.findByHymn_Number(number))
                .willReturn(Optional.of(audio));

        given(mapper.toAudioResponse(audio))
                .willReturn(response);

        AudioDto result = service.findAudioByNumber(number);

        assertEquals(response, result);

        then(audioStorage).shouldHaveNoInteractions();
        then(audioRepository).should(never()).save(any());
    }

    @Test
    void shouldLoadAudioFromStorageAndPersist() {

        int number = 15;

        Hymn hymn = new Hymn();
        Audio audio = new Audio();

        AudioDto response = new AudioDto("url", 500, 200);
        HymnDto hymnDto = new HymnDto();

        given(audioRepository.findByHymn_Number(number))
                .willReturn(Optional.empty());

        given(audioStorage.getAudioByNumberFromStorage(number))
                .willReturn(Optional.of(response));

        given(hymnService.findHymnByNumber(number))
                .willReturn(hymnDto);

        given(mapper.toAudio(response))
                .willReturn(audio);

        given(hymnMapper.toHymnEntity(hymnDto))
                .willReturn(hymn);

        AudioDto result = service.findAudioByNumber(number);

        assertEquals(response, result);

        ArgumentCaptor<Audio> captor =
                ArgumentCaptor.forClass(Audio.class);

        then(audioRepository).should().save(captor.capture());

        Audio saved = captor.getValue();

        assertEquals(hymn, saved.getHymn());
    }

    @Test
    void shouldThrowExceptionWhenAudioDoesNotExistInStorage() {

        int number = 20;

        given(audioRepository.findByHymn_Number(number))
                .willReturn(Optional.empty());

        given(audioStorage.getAudioByNumberFromStorage(number))
                .willReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class, () -> service.findAudioByNumber(number)
        );

        then(audioRepository).should(never()).save(any());
    }

    @Test
    @DisplayName("Deve lançar ResourceNotFoundException quando o hino não existir")
    void shouldThrowExceptionWhenHymnDoesNotExist() {

        int number = 25;

        AudioDto response = new AudioDto("url", 200, 100);

        given(audioRepository.findByHymn_Number(number))
                .willReturn(Optional.empty());

        given(audioStorage.getAudioByNumberFromStorage(number))
                .willReturn(Optional.of(response));

        given(hymnService.findHymnByNumber(number))
                .willThrow(new ResourceNotFoundException(
                        "Hino de número 25 não encontrado!"
                ));

        assertThrows(
                ResourceNotFoundException.class,
                () -> service.findAudioByNumber(number)
        );

        then(audioRepository).should().findByHymn_Number(number);
        then(audioStorage).should().getAudioByNumberFromStorage(number);
        then(hymnService).should().findHymnByNumber(number);

        then(audioRepository).should(never()).save(any(Audio.class));
    }

}