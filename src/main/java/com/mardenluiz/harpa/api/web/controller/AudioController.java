package com.mardenluiz.harpa.api.web.controller;

import com.mardenluiz.harpa.api.web.dto.AudioDto;
import com.mardenluiz.harpa.api.domain.service.AudioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Validated
@RestController
@RequestMapping(value = "/api/v1/audio")
public class AudioController {

    private final AudioService service;

    public AudioController(AudioService service) {
        this.service = service;
    }

    @GetMapping("/{number}")
    public ResponseEntity<AudioDto> findByNumber(@PathVariable(name = "number")
                                                          @Min(value = 1, message = "O número do hino deve ser maior que zero.")
                                                          @Max(value = 640, message = "O número do hino deve ser menor ou igual a 640.")
                                                          int number) {
        return ResponseEntity.ok(service.findAudioByNumber(number));
    }
}
