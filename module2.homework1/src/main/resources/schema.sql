DROP TABLE IF EXISTS AUTHORS, BOOKS, GENRES;
CREATE TABLE AUTHORS (
    ID INT PRIMARY KEY,
    NAME VARCHAR(255),
    LASTNAME VARCHAR(255)
);
CREATE TABLE GENRES (
    ID INT PRIMARY KEY,
    NAME VARCHAR(255)
);
CREATE TABLE BOOKS (
    ID INT PRIMARY KEY,
    NAME VARCHAR(255),
    GENRE BIGINT,
    FOREIGN KEY (GENRE) REFERENCES GENRES(ID),
    AUTHOR BIGINT,
    FOREIGN KEY (AUTHOR) REFERENCES AUTHORS(ID)
);
