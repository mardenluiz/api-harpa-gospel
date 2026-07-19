package com.mardenluiz.harpa.api.domain.service;

import com.mardenluiz.harpa.api.domain.model.Hymn;
import com.mardenluiz.harpa.api.api.dto.HymnDto;
import com.mardenluiz.harpa.api.api.dto.PageResponse;
import com.mardenluiz.harpa.api.api.mapstruct.HymnMapper;
import com.mardenluiz.harpa.api.domain.repository.AudioRepository;
import com.mardenluiz.harpa.api.domain.repository.HymnRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HymnService {

    private final HymnRepository hymnRepository;
    private final AudioService audioService;
    private final AudioRepository audioRepository;
    private final HymnMapper mapper;


    @Transactional
    public HymnDto findHymnByNumber(int number) {

        if (audioRepository.existsByHymn_Number(number)) {
            return hymnRepository.findByNumber(number)
                    .map(mapper::toHymnDto)
                    .orElseThrow(() -> new NoSuchElementException("Elemento não existe!"));
        }

        return null;
    }

    public PageResponse<T> findAll(Pageable pageable) {

        Page<Hymn> page = hymnRepository.findAll(pageable);

        return new PageResponse<>(
                page.getContent()
                        .stream()
                        .map(mapper::toHymnDto)
                        .toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

    public HymnDto findByTitle(@Valid String title) {
              return hymnRepository.searchByTitle(title)
                     .map(mapper::toHymnDto)
                     .orElseThrow(() -> new NoSuchElementException("Hino não encontrado!"));
    }
}
