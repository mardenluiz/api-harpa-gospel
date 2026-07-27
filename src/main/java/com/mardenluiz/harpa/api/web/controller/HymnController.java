package com.mardenluiz.harpa.api.web.controller;

import com.mardenluiz.harpa.api.doc.HymnControllerOpenApi;
import com.mardenluiz.harpa.api.web.dto.HymnDto;
import com.mardenluiz.harpa.api.web.dto.PageResponse;
import com.mardenluiz.harpa.api.domain.service.HymnService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/hymn")
public class HymnController implements HymnControllerOpenApi {

    private final HymnService hymnService;

    @GetMapping("/{number}")
    public ResponseEntity<HymnDto> findHymnByNumber(@PathVariable(name = "number") int number) {
        return ResponseEntity.ok(hymnService.findHymnByNumber(number));
    }

    @GetMapping("/all")
    public ResponseEntity<PageResponse<HymnDto>> findAll(@PageableDefault(size = 10, sort = "number") Pageable pageable) {
        return ResponseEntity.ok(hymnService.findAll(pageable));
    }

    @GetMapping("/search")
    public ResponseEntity<HymnDto> findByTitle(@RequestParam(name = "title")
                                               @NotBlank(message = "O nome não pode ser nulo ou em branco") String title) {
        return ResponseEntity.ok(hymnService.findByTitle(title));
    }

    @PostMapping("/clean-cache")
    public ResponseEntity<Void> cleanCache() {
        hymnService.cleanCache();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

