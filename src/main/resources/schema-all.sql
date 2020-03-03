DROP TABLE people IF EXISTS;
DROP TABLE person IF EXISTS;

CREATE TABLE people  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

CREATE TABLE person  (
    person_id BIGINT IDENTITY NOT NULL PRIMARY KEY,
    first_name VARCHAR(20),
    last_name VARCHAR(20)
);

INSERT INTO person (first_name, last_name) VALUES ('Lou', 'Sassole');
INSERT INTO person (first_name, last_name) VALUES ('Mike', 'Hunt');
INSERT INTO person (first_name, last_name) VALUES ('Ben', 'Dover');
INSERT INTO person (first_name, last_name) VALUES ('Al', 'Caholic');
INSERT INTO person (first_name, last_name) VALUES ('Teflonda', 'Slick');
INSERT INTO person (first_name, last_name) VALUES ('Amanda', 'Hugginkiss');
INSERT INTO person (first_name, last_name) VALUES ('Hugh', 'Jass');
INSERT INTO person (first_name, last_name) VALUES ('jackie', 'kneoff');
INSERT INTO person (first_name, last_name) VALUES ('Ice', 'Ukk');
INSERT INTO person (first_name, last_name) VALUES ('Ben', 'Gerkin');