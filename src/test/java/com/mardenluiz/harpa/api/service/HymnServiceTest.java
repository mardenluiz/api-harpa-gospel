package com.mardenluiz.harpa.api.service;

import com.mardenluiz.harpa.api.domain.exception.ResourceNotFoundException;
import com.mardenluiz.harpa.api.domain.service.AudioService;
import com.mardenluiz.harpa.api.domain.service.HymnService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import com.mardenluiz.harpa.api.domain.model.Hymn;
import com.mardenluiz.harpa.api.web.dto.HymnDto;
import com.mardenluiz.harpa.api.web.dto.PageResponse;
import com.mardenluiz.harpa.api.web.mapstruct.HymnMapper;
import com.mardenluiz.harpa.api.domain.repository.AudioRepository;
import com.mardenluiz.harpa.api.domain.repository.HymnRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HymnServiceTest {

    @Mock
    private HymnRepository hymnRepository;

    @Mock
    private AudioRepository audioRepository;

    @InjectMocks
    private AudioService audioService;

    @Mock
    private HymnMapper mapper;

    @InjectMocks
    private HymnService hymnService;

    private Hymn hymn;
    private HymnDto hymnDto;

    @BeforeEach
    void setup() {

        hymn = new Hymn();
        hymn.setNumber(1);
        hymn.setTitle("Mais Perto Quero Estar");

        hymnDto = new HymnDto();
        hymnDto.setNumber(1);
        hymnDto.setTitle("Mais Perto Quero Estar");
    }

    @Test
    @DisplayName("Deve retornar um hino quando existir áudio")
    void shouldReturnHymnWhenAudioExists() {

        when(hymnRepository.findByNumber(1))
                .thenReturn(Optional.of(hymn));

        when(mapper.toHymnDto(hymn))
                .thenReturn(hymnDto);

        HymnDto response = hymnService.findHymnByNumber(1);

        assertNotNull(response);
        assertEquals(1, response.getNumber());
        assertEquals("Mais Perto Quero Estar", response.getTitle());

        verify(hymnRepository).findByNumber(1);
        verify(mapper).toHymnDto(hymn);
    }

    @Test
    @DisplayName("Deve retornar null quando não existir áudio")
    void shouldReturnNullWhenAudioDoesNotExist() {

        when(hymnRepository.findByNumber(1))
                .thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class,
                () -> hymnService.findHymnByNumber(1)
        );

        assertEquals("Hino de número 1 não encontrado!", exception.getMessage());

        verify(hymnRepository).findByNumber(1);
        verifyNoInteractions(mapper);
    }

    @Test
    @DisplayName("Deve retornar página de hinos")
    void shouldReturnPagedHymns() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Hymn> page = new PageImpl<>(List.of(hymn), pageable, 1);

        when(hymnRepository.findAll(pageable))
                .thenReturn(page);

        when(mapper.toHymnDto(hymn))
                .thenReturn(hymnDto);

        PageResponse<?> response = hymnService.findAll(pageable);

        assertNotNull(response);

        assertEquals(0, response.page());
        assertEquals(10, response.size());
        assertEquals(1, response.totalElements());
        assertEquals(1, response.totalPages());

        assertTrue(response.first());
        assertTrue(response.last());

        assertEquals(1, response.content().size());

        verify(hymnRepository).findAll(pageable);
        verify(mapper).toHymnDto(hymn);
    }

    @Test
    @DisplayName("Deve retornar página vazia")
    void shouldReturnEmptyPage() {

        Pageable pageable = PageRequest.of(0, 10);

        Page<Hymn> page = Page.empty(pageable);

        when(hymnRepository.findAll(pageable))
                .thenReturn(page);

        PageResponse<?> response = hymnService.findAll(pageable);

        assertNotNull(response);
        assertTrue(response.content().isEmpty());

        assertEquals(0, response.totalElements());
        assertEquals(0, response.totalPages());

        assertTrue(response.first());
        assertTrue(response.last());

        verify(hymnRepository).findAll(pageable);
        verify(mapper, never()).toHymnDto(any());
    }

}