package com.mardenluiz.harpa.api.api.controller;

import com.mardenluiz.harpa.api.api.dto.HymnDto;
import com.mardenluiz.harpa.api.api.dto.PageResponse;
import com.mardenluiz.harpa.api.domain.service.HymnService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/hymn")
public class HymnController {

    private final HymnService hymnService;

    @GetMapping("/{number}")
    public ResponseEntity<HymnDto> findHymn(@PathVariable(name = "number") @Valid int number) {
        return ResponseEntity.ok(hymnService.findHymnByNumber(number));
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<T>> findAll(@PageableDefault(size = 10, sort = "number") Pageable pageable) {
        return ResponseEntity.ok(hymnService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<HymnDto> findByTitle(@RequestParam(name = "title") @Valid String title) {
        return ResponseEntity.ok(hymnService.findByTitle(title));
    }
}

