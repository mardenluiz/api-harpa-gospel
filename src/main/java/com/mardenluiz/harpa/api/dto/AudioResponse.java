package com.mardenluiz.harpa.api.dto;

public record AudioResponse(
        String url,
        Double sizeMb,
        double durationMs
) {
}