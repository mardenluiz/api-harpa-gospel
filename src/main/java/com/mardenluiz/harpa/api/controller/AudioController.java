package com.mardenluiz.harpa.api.controller;

import com.mardenluiz.harpa.api.dto.AudioDto;
import com.mardenluiz.harpa.api.service.AudioService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return ResponseEntity.ok(service.findByNumber(number));
    }
}
