package com.mardenluiz.harpa.api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class HymnJson {

    @JsonProperty("hymn")
    private String hymn;

    @JsonProperty("chorus")
    private String chorus;

    @JsonProperty("verses")
    private Map<String, String> verses;

    public String getHymn() {
        return hymn;
    }

    public void setHymn(String hymn) {
        this.hymn = hymn;
    }

    public String getChorus() {
        return chorus;
    }

    public void setChorus(String chorus) {
        this.chorus = chorus;
    }

    public Map<String, String> getVerses() {
        return verses;
    }

    public void setVerses(Map<String, String> verses) {
        this.verses = verses;
    }
}
