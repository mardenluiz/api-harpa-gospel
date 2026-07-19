package com.mardenluiz.harpa.api.domain.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hymn_verse", uniqueConstraints = {@UniqueConstraint(columnNames = {"hymn_id","verse_number"})})
public class HymnVerse {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private Integer verseNumber;
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hymn_id", nullable = false)
    private Hymn hymn;

}
