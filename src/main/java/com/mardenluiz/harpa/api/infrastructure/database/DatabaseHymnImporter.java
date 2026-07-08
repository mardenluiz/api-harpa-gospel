package com.mardenluiz.harpa.api.infrastructure.database;


import com.mardenluiz.harpa.api.domain.Hymn;
import com.mardenluiz.harpa.api.domain.HymnVerse;
import com.mardenluiz.harpa.api.repository.HymnRepository;
import jakarta.transaction.Transactional;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.InputStream;
import java.util.*;

@Service
public class DatabaseHymnImporter {

    private final HymnRepository hymnRepository;
    private final ObjectMapper objectMapper;

    public DatabaseHymnImporter(HymnRepository hymnRepository, ObjectMapper objectMapper) {
        this.hymnRepository = hymnRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void importJson() throws Exception {

        if (hymnRepository.count() > 0) {
            return;
        }

        InputStream input = new ClassPathResource("data/harpa_crista_640_hinos.json")
                        .getInputStream();

        Map<String, HymnJson> json = objectMapper.readValue(input, new TypeReference<>() {});

        List<Hymn> hymns = new ArrayList<>();

        for (Map.Entry<String, HymnJson> entry : json.entrySet()) {
            int number = Integer.parseInt(entry.getKey());
            HymnJson dto = entry.getValue();
            String title = dto.getHymn()
                    .replaceFirst("^\\d+\\s*-\\s*", "")
                    .trim();

            Hymn hymn = new Hymn();

            hymn.setNumber(number);
            hymn.setTitle(title);
            hymn.setChorus(dto.getChorus());

            List<HymnVerse> verses = new ArrayList<>();

            dto.getVerses()
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey(Comparator.comparingInt(Integer::parseInt)))
                    .forEach(v -> {

                        HymnVerse verse = new HymnVerse();

                        verse.setVerseNumber(Integer.parseInt(v.getKey()));
                        verse.setContent(v.getValue());
                        verse.setHymn(hymn);
                        verses.add(verse);
                    });

            hymn.setVerses(verses);
            hymns.add(hymn);
        }

        hymnRepository.saveAll(hymns);

    }

}
