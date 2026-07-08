package com.mardenluiz.harpa.api.infrastructure.database;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ImporterRun implements CommandLineRunner {

    private final DatabaseHymnImporter importer;

    public ImporterRun(DatabaseHymnImporter importer) {
        this.importer = importer;
    }

    @Override
    public void run(String... args) throws Exception {
        importer.importJson();
    }

}