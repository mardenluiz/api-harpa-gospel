package com.mardenluiz.harpa.api.web.dto;

public record AudioDto(
        String url,
        long size,
        long duration
) {

}