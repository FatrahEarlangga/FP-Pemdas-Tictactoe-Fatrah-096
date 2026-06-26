import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    // TODO: Sesuaikan URL, USER, dan PASSWORD dengan konfigurasi PostgreSQL kamu
    private static final String URL = "jdbc:postgresql://localhost:5432/game_project";
    private static final String USER = "postgres";
    private static final String PASSWORD = "Fatrah05";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void initializeDatabase() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS players (" +
                "id SERIAL PRIMARY KEY, " +
                "username VARCHAR(50) UNIQUE NOT NULL, " +
                "password VARCHAR(50) NOT NULL, " +
                "wins INTEGER DEFAULT 0, " +
                "losses INTEGER DEFAULT 0, " +
                "draws INTEGER DEFAULT 0, " +
                "score INTEGER DEFAULT 0" +
                ");";
        try (Connection conn = getConnection();
                Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        }
    }
}
