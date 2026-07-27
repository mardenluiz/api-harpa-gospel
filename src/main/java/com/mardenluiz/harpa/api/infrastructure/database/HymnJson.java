package com.mardenluiz.harpa.api.infrastructure.database;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class HymnJson {

    @JsonProperty("hymn")
    private String hymn;

    @JsonProperty("chorus")
    private String chorus;

    @JsonProperty("verses")
    private Map<String, String> verses;

}
