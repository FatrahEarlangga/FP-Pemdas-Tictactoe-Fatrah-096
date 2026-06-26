# Simple Tic-Tac-Toe Game with Java Swing, Login, and Statistics

## Student Information
| Field      | Isi                                      |
|------------|------------------------------------------|
| Name       | Fatrah Earlangga Pratama                 |
| Student ID | 5026251096                               |
| Class      | A                                        |


## Project Description
This project is a simple Tic-Tac-Toe game built using Java Swing. The application features a login system connected to a PostgreSQL database, game statistics tracking, and a Top 5 scorers leaderboard. The player plays as X against a computer opponent (O).

## Features
- Login using database (PostgreSQL)
- Play Tic-Tac-Toe with a smart computer opponent (blocks player wins, tries to win)
- Record wins, losses, draws, and score after each game
- Win = +10 points, Draw = +3 points, Lose = +0 points
- Display personal statistics
- Display Top 5 scorers using JTable, sorted by score then wins

## Database
**Database used:** PostgreSQL

## How to Create the Database
1. Open pgAdmin or psql terminal
2. Run the following commands:
```sql
CREATE DATABASE game_project;
\c game_project
CREATE TABLE players (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    wins INT DEFAULT 0,
    losses INT DEFAULT 0,
    draws INT DEFAULT 0,
    score INT DEFAULT 0
);
INSERT INTO players (username, password) VALUES ('student1', '12345');
INSERT INTO players (username, password) VALUES ('student2', '12345');
```
Or simply run the file `database/schema.sql`

## How to Run
1. Make sure PostgreSQL is running on your computer
2. Create the database using the SQL above
3. Download the PostgreSQL JDBC driver from https://jdbc.postgresql.org/
4. Add the JDBC .jar file to your Java project's classpath
5. Open `src/DatabaseManager.java` and change:
   - `USER` → your PostgreSQL username (default: `postgres`)
   - `PASSWORD` → your PostgreSQL password
6. Compile all `.java` files in the `src/` folder
7. Run `Main.java`
8. Login with username: `student1`, password: `12345`

## Class Explanation
| Class | Responsibility |
|---|---|
| `Main` | Entry point — opens the Login Window |
| `DatabaseManager` | Manages JDBC connection to PostgreSQL |
| `Player` | Model class — stores player data (id, username, wins, losses, draws, score) |
| `PlayerService` | Handles login, statistics update, and Top 5 query |
| `GameLogic` | Game rules: move validation, win/draw detection, computer AI |
| `LoginFrame` | Swing window for login (username + password) |
| `MainMenuFrame` | Swing window for main menu after login |
| `GameFrame` | Swing window for playing Tic-Tac-Toe (3x3 button grid) |
| `StatisticsFrame` | Swing window showing personal statistics |
| `TopScorersFrame` | Swing window showing Top 5 scorers in JTable |

## Score Calculation
| Result | Score Change |
|---|---|
| Win | +10 points |
| Draw | +3 points |
| Lose | +0 points |

## Screenshots
### Login & Register
| Form Login | Login Berhasil | Login Gagal | Register |
|:---:|:---:|:---:|:---:|
| <img width="481" alt="image" src="https://github.com/user-attachments/assets/6f752310-a7b8-453e-b8f8-9480872dc40b" /> | <img width="478" alt="image" src="https://github.com/user-attachments/assets/7c423323-79ec-4885-946c-13ae2d003daa" /> | <img width="480" alt="image" src="https://github.com/user-attachments/assets/b75bea74-24af-4cb0-aae9-2518f67cc216" /> | <img width="517" height="368" alt="image" src="https://github.com/user-attachments/assets/e6eed6c2-17e8-43c5-810e-dc1a07a65ea3" /> |


### Main Menu & Game
| Main Menu | Mulai Game |
|:---:|:---:|
| <img width="481" height="461" alt="image" src="https://github.com/user-attachments/assets/135b7f1a-2d55-4fdd-8ff2-6b5857cc377b" />| <img width="505" height="636" alt="image" src="https://github.com/user-attachments/assets/665ac33d-2afb-4973-9174-ef049f2b8e53" /> |

### Kondisi Game
| Menang | Kalah | Draw |
|:---:|:---:|:---:|
| <img width="507" height="637" alt="image" src="https://github.com/user-attachments/assets/8ae10197-8504-4fcb-9cb2-55380483ce5c" /> | <img width="507" height="637" alt="image" src="https://github.com/user-attachments/assets/1e41f89f-98bf-44e3-b6d1-1fcf8c3466fc" /> | <img width="507" height="642" alt="image" src="https://github.com/user-attachments/assets/07d3bced-09cf-4d60-87cd-d7e8dd14f337" /> |

### Statistik & Leaderboard
| Statistik | Top 5 Scorers | Keluar |
|:---:|:---:|:---:|
| <img width="482" height="462" alt="image" src="https://github.com/user-attachments/assets/ec6f756d-1eb6-43be-9780-85e51cd2d655" /> | <img width="608" height="426" alt="image" src="https://github.com/user-attachments/assets/ad7a3e40-d46f-4f1e-8acd-3d3f9af61058" /> | <img width="480" height="462" alt="image" src="https://github.com/user-attachments/assets/376a0bcb-d067-487d-87f1-fe2f1f0eab38" /> |

## GitHub Repository
[Link GitHub kamu di sini]

## YouTube Video
[[Link YouTube kamu di sini]](https://youtu.be/-L_WBY1gO8U)
