CREATE INDEX idx_hymn_number
    ON hymn(number);

CREATE INDEX idx_audio_hymn_number
    ON audio(hymn_number);

CREATE INDEX idx_hymn_verse_hymn
    ON hymn_verse(hymn_id);

CREATE INDEX idx_hymn_verse_number
    ON hymn_verse(verse_number);