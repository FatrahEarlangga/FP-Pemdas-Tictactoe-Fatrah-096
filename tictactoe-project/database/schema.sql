-- PostgreSQL Schema for Tic-Tac-Toe Game Project
-- ES234211 - Programming Fundamental

CREATE DATABASE game_project;

\c game_project;

CREATE TABLE players (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    wins INT DEFAULT 0,
    losses INT DEFAULT 0,
    draws INT DEFAULT 0,
    score INT DEFAULT 0
);

-- Sample players for testing
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student1', '12345', 0, 0, 0, 0);
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student2', '12345', 0, 0, 0, 0);
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student3', '12345', 0, 0, 0, 0);
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student4', '12345', 0, 0, 0, 0);
INSERT INTO players (username, password, wins, losses, draws, score) VALUES ('student5', '12345', 0, 0, 0, 0);
