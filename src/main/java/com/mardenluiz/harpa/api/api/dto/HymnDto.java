package com.mardenluiz.harpa.api.api.dto;

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

    @NotBlank(message = "O nome não pode ser nulo ou em branco")
    private String title;
    @Min(value = 1, message = "O número do hino deve ser maior que zero.")
    @Max(value = 640, message = "O número do hino deve ser menor ou igual a 640.")
    private int number;
    private String chorus;
    private List<VerseDto> verses = new ArrayList<>();
    private AudioDto audio;
}
