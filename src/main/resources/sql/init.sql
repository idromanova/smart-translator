-- CREATE DATABASE smart_translator;

CREATE TABLE IF NOT EXISTS words (
    id BIGINT PRIMARY KEY,
    text VARCHAR(50) NOT NULL,
    language VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS translations (
    id BIGINT PRIMARY KEY,
    original_word_id INT NOT NULL REFERENCES words(id),
    translated_word_id INT NOT NULL REFERENCES words(id)
);
