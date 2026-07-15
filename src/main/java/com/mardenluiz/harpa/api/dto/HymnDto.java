package com.mardenluiz.harpa.api.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import java.util.ArrayList;
import java.util.List;

@ToString
@Getter
@Setter
@JsonPropertyOrder({"number", "title", "chorus", "author", "meter", "verses", "audio"})
public class HymnDto {

    private String title;
    @Min(value = 1, message = "O número do hino deve ser maior que zero.")
    @Max(value = 640, message = "O número do hino deve ser menor ou igual a 640.")
    private int number;
    private String chorus;

    private List<VerseDto> verses = new ArrayList<>();

    private AudioDto audio;
}
