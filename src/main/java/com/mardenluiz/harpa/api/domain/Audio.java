package com.mardenluiz.harpa.api.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "audio", uniqueConstraints = {@UniqueConstraint(name = "uk_audio_hymn", columnNames = "hymn_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Audio {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String url;
    private double duration;
    private double size;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "hymn_number",
            referencedColumnName = "number",
            nullable = false,
            unique = true
    )
    private Hymn hymn;

}
