package com.mardenluiz.harpa.api.controller;

import com.mardenluiz.harpa.api.dto.HymnDto;
import com.mardenluiz.harpa.api.dto.PageResponse;
import com.mardenluiz.harpa.api.service.HymnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/hymn")
public class HymnController {

    private final HymnService hymnService;

    @GetMapping("/{number}")
    public HymnDto findHymn(@PathVariable(name = "number") @Valid int number) {
        return hymnService.findHymnByNumber(number);
    }

    @GetMapping
    public ResponseEntity<PageResponse<T>> findAll(@PageableDefault(size = 10, sort = "number") Pageable pageable) {
        return ResponseEntity.ok(hymnService.findAll(pageable));
    }
}
