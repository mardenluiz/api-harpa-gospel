package com.mardenluiz.harpa.api.web.controller;

import com.mardenluiz.harpa.api.web.dto.HymnDto;
import com.mardenluiz.harpa.api.web.dto.PageResponse;
import com.mardenluiz.harpa.api.domain.service.HymnService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/hymn")
public class HymnController {

    private final HymnService hymnService;

    @GetMapping("/{number}")
    public ResponseEntity<HymnDto> findHymn(@PathVariable(name = "number")
                                                @Min(value = 1, message = "O número do hino deve ser maior que zero.")
                                                @Max(value = 640, message = "O número do hino deve ser menor ou igual a 640.")
                                                int number) {
        return ResponseEntity.ok(hymnService.findHymnByNumber(number));
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<T>> findAll(@PageableDefault(size = 10, sort = "number") Pageable pageable) {
        return ResponseEntity.ok(hymnService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<HymnDto> findByTitle(@RequestParam(name = "title")
                                                   @NotBlank(message = "O nome não pode ser nulo ou em branco") String title) {
        return ResponseEntity.ok(hymnService.findByTitle(title));
    }
}

