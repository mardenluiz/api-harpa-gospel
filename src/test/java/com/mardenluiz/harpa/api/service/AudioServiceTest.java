package com.mardenluiz.harpa.api.service;

import com.mardenluiz.harpa.api.domain.Audio;
import com.mardenluiz.harpa.api.domain.Hymn;
import com.mardenluiz.harpa.api.infrastructure.storage.impl.AudioStorageImpl;
import org.hibernate.ObjectNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.mardenluiz.harpa.api.dto.AudioResponse;
import com.mardenluiz.harpa.api.dto.mapstruct.AudioMapper;

import com.mardenluiz.harpa.api.repository.AudioRepository;
import com.mardenluiz.harpa.api.repository.HymnRepository;
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
    private AudioRepository audioRepository;

    @Mock
    private HymnRepository hymnRepository;

    @Mock
    private AudioStorageImpl audioStorage;

    @InjectMocks
    private AudioService service;

    @Test
    void shouldReturnAudioFromDatabase() {

        int number = 10;

        Audio audio = new Audio();
        AudioResponse response = new AudioResponse("url", 100, 120);

        given(audioRepository.findByHymn_Number(number))
                .willReturn(Optional.of(audio));

        given(mapper.audioToAudioResponse(audio))
                .willReturn(response);

        AudioResponse result = service.findByNumber(number);

        assertEquals(response, result);

        then(audioStorage).shouldHaveNoInteractions();
        then(audioRepository).should(never()).save(any());
    }

    @Test
    void shouldLoadAudioFromStorageAndPersist() {

        int number = 15;

        Hymn hymn = new Hymn();

        Audio audio = new Audio();

        AudioResponse response =
                new AudioResponse("url", 500, 200);

        given(audioRepository.findByHymn_Number(number))
                .willReturn(Optional.empty());

        given(audioStorage.getAudioByNumberFromStorage(number))
                .willReturn(Optional.of(response));

        given(hymnRepository.findByNumber(number))
                .willReturn(Optional.of(hymn));

        given(mapper.toAudio(response))
                .willReturn(audio);

        AudioResponse result = service.findByNumber(number);

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
                ObjectNotFoundException.class, () -> service.findByNumber(number)
        );

        then(audioRepository).should(never()).save(any());
    }

    @Test
    void shouldThrowExceptionWhenHymnDoesNotExist() {

        int number = 25;

        AudioResponse response =
                new AudioResponse("url", 200, 100);

        given(audioRepository.findByHymn_Number(number))
                .willReturn(Optional.empty());

        given(audioStorage.getAudioByNumberFromStorage(number))
                .willReturn(Optional.of(response));

        given(hymnRepository.findByNumber(number))
                .willReturn(Optional.empty());

        assertThrows(
                ObjectNotFoundException.class,
                () -> service.findByNumber(number)
        );

        then(audioRepository).should(never()).save(any());
    }

    @Test
    void shouldReturnHymn() {

        int number = 30;

        Hymn hymn = new Hymn();

        given(hymnRepository.findByNumber(number))
                .willReturn(Optional.of(hymn));

        Hymn result = service.findHymn(number);

        assertEquals(hymn, result);
    }

    @Test
    void shouldThrowExceptionWhenHymnNotFound() {

        int number = 40;

        given(hymnRepository.findByNumber(number))
                .willReturn(Optional.empty());

        assertThrows(
                ObjectNotFoundException.class,
                () -> service.findHymn(number)
        );
    }

}