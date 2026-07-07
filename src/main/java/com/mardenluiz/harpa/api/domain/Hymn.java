package com.mardenluiz.harpa.api.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Hymn {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private int number;
    @Column(columnDefinition = "TEXT")
    private String chorus;

    @OneToMany(
            mappedBy = "hymn",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    @OrderBy("verseNumber ASC")
    @Builder.Default
    private List<HymnVerse> verses = new ArrayList<>();

    @OneToOne(
            mappedBy = "hymn",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Audio audio;

    public void addVerse(HymnVerse verse){
        verse.setHymn(this);
        verses.add(verse);
    }

    public void setAudio(Audio audio) {
        if (audio != null) {
            audio.setHymn(this);
        }
        this.audio = audio;
    }
}
