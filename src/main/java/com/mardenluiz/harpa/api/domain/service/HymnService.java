package com.mardenluiz.harpa.api.domain.service;


import com.mardenluiz.harpa.api.domain.exception.ResourceNotFoundException;
import com.mardenluiz.harpa.api.domain.model.Hymn;
import com.mardenluiz.harpa.api.web.dto.HymnDto;
import com.mardenluiz.harpa.api.web.dto.PageResponse;
import com.mardenluiz.harpa.api.web.mapstruct.HymnMapper;
import com.mardenluiz.harpa.api.domain.repository.HymnRepository;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HymnService {

    private final HymnRepository hymnRepository;
    private final HymnMapper mapper;


    public HymnDto findHymnByNumber(int number) {

        return hymnRepository.findByNumber(number)
                .map(mapper::toHymnDto)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Hino de número " + number + " não encontrado!")
                );

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


    public HymnDto findByTitle(String title) {
              return hymnRepository.searchByTitle(title)
                     .map(mapper::toHymnDto)
                     .orElseThrow(() -> new ResourceNotFoundException(String.format(
                             "Hino com o titulo '%s' não encontrado!", title
                     )));
    }
}
