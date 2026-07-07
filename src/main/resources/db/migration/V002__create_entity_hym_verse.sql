CREATE TABLE hymn_verse (
     id UUID PRIMARY KEY,
     verse_number INTEGER NOT NULL,
     content TEXT NOT NULL,
     hymn_id UUID NOT NULL,

     CONSTRAINT fk_hymn
         FOREIGN KEY (hymn_id)
             REFERENCES hymn(id)
             ON DELETE CASCADE,

     CONSTRAINT uk_hymn_verse
         UNIQUE(hymn_id, verse_number)
);