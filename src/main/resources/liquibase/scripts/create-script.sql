-- liquibase formatted sql

--changeset smakrushin:3

CREATE TABLE Human
(
Id INTEGER PRIMARY KEY,
Name TEXT,
Age INTEGER DEFAULT 18 CHECK(Age >0 AND Age < 100),
Rights BOOLEAN NOT null,
CarId INTEGER REFERENCES Cars(Id)
);

--changeset smakrushin:4
DROP TABLE Human;