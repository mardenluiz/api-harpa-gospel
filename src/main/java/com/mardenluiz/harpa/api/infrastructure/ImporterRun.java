package com.mardenluiz.harpa.api.infrastructure;


import com.mardenluiz.harpa.api.infrastructure.database.DatabaseHymnImporter;
import com.mardenluiz.harpa.api.infrastructure.storage.impl.AudioStorageImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
@RequiredArgsConstructor
public class ImporterRun implements CommandLineRunner {

    private final DatabaseHymnImporter importer;
    private final AudioStorageImpl audioStorage;


    @Override
    public void run(String... args) throws Exception {
        importer.importJson();
        audioStorage.importAudios();
    }

}