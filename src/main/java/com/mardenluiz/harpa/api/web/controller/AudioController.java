package com.mardenluiz.harpa.api.web.controller;

import com.mardenluiz.harpa.api.doc.AudioControllerOpenApi;
import com.mardenluiz.harpa.api.web.dto.AudioDto;
import com.mardenluiz.harpa.api.domain.service.AudioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Validated
@RestController
@RequestMapping(value = "/api/v1/audio")
public class AudioController implements AudioControllerOpenApi {

    private final AudioService service;

    public AudioController(AudioService service) {
        this.service = service;
    }

    @GetMapping("/{number}")
    public ResponseEntity<AudioDto> findByNumber(@PathVariable(name = "number")
                                                 int number) {
        return ResponseEntity.ok(service.findAudioByNumber(number));
    }

    @PostMapping("/clean-cache")
    public ResponseEntity<Void> cleanCache() {
        service.cleanCache();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
