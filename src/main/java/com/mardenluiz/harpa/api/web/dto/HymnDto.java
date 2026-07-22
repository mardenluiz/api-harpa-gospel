package com.mardenluiz.harpa.api.web.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    private int number;
    private String chorus;
    private List<VerseDto> verses = new ArrayList<>();
    private AudioDto audio;
}
