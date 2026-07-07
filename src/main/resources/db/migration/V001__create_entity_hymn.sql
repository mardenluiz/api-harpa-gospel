CREATE TABLE hymn (
   id UUID PRIMARY KEY,
   title VARCHAR(255) NOT NULL,
   number INTEGER NOT NULL,
   chorus TEXT,

   CONSTRAINT uk_hymn_number UNIQUE (number)
);
