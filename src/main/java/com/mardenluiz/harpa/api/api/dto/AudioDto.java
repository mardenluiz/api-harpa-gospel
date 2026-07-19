package com.mardenluiz.harpa.api.api.dto;

public record AudioDto(
        String url,
        long size,
        long duration
) {

}