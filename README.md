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

### Login
**| Form Login | Login Berhasil | Login Gagal | Register |**
|:---:|:---:|:---:|:---:|
| <img width="481" height="363" alt="image" src="https://github.com/user-attachments/assets/6f752310-a7b8-453e-b8f8-9480872dc40b" /> | <img width="478" height="362" alt="image" src="https://github.com/user-attachments/assets/7c423323-79ec-4885-946c-13ae2d003daa" /> | <img width="480" height="363" alt="image" src="https://github.com/user-attachments/assets/b75bea74-24af-4cb0-aae9-2518f67cc216" />| <img width="508" height="366" alt="image" src="https://github.com/user-attachments/assets/f8979355-597b-4d9c-b30e-a0af170395ec" /> |


### Main Menu & Game
| Main Menu | Mulai Game |
|:---:|:---:|
| <img src="..." width="250"/> | <img src="..." width="250"/> |

### Kondisi Game
| Menang | Kalah | Draw |
|:---:|:---:|:---:|
| <img src="..." width="250"/> | <img src="..." width="250"/> | <img src="..." width="250"/> |

### Statistik & Leaderboard
| Statistik | Top 5 Scorers | Keluar |
|:---:|:---:|:---:|
| <img src="..." width="250"/> | <img src="..." width="250"/> | <img src="..." width="250"/> |

## GitHub Repository
[Link GitHub kamu di sini]

## YouTube Video
[Link YouTube kamu di sini]
