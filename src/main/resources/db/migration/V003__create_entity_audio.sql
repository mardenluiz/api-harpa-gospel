CREATE TABLE audio (
    id UUID PRIMARY KEY,
    url TEXT NOT NULL,
    duration INTEGER,
    size BIGINT,
    hymn_number INTEGER UNIQUE NOT NULL,

    CONSTRAINT uk_audio_hymn UNIQUE (hymn_number),

    CONSTRAINT fk_audio_hymn
        FOREIGN KEY (hymn_number)
            REFERENCES hymn(number)
            ON DELETE CASCADE
);