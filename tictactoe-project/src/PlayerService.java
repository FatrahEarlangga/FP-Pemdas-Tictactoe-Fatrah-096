import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PlayerService {

    // Login: cek username dan password dari database
    public Player login(String username, String password) throws SQLException {
        String sql = "SELECT * FROM players WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String uname = rs.getString("username");
                    int wins = rs.getInt("wins");
                    int losses = rs.getInt("losses");
                    int draws = rs.getInt("draws");
                    int score = rs.getInt("score");
                    return new Player(id, uname, wins, losses, draws, score);
                }
            }
        }
        return null;
    }

    // Register: tambah player baru ke database jika username belum terdaftar
    public boolean register(String username, String password) throws SQLException {
        String checkSql = "SELECT COUNT(*) FROM players WHERE username = ?";
        String insertSql = "INSERT INTO players (username, password, wins, losses, draws, score) VALUES (?, ?, 0, 0, 0, 0)";
        
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        return false; // Username sudah terpakai
                    }
                }
            }
            
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, password);
                insertStmt.executeUpdate();
                return true; // Pendaftaran berhasil
            }
        }
    }

    // Update statistik setelah game selesai
    public void updateStatistics(Player player, String result) {
        int additionalScore = 0;
        String sql = "";

        if (result.equalsIgnoreCase("WIN")) {
            additionalScore = 10;
            sql = "UPDATE players SET wins = wins + 1, score = score + ? WHERE id = ?";
        } else if (result.equalsIgnoreCase("LOSE")) {
            additionalScore = 0;
            sql = "UPDATE players SET losses = losses + 1, score = score + ? WHERE id = ?";
        } else if (result.equalsIgnoreCase("DRAW")) {
            additionalScore = 3;
            sql = "UPDATE players SET draws = draws + 1, score = score + ? WHERE id = ?";
        }

        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, additionalScore);
            stmt.setInt(2, player.getId());
            stmt.executeUpdate();
            conn.close();
        } catch (Exception e) {
            System.out.println("Update statistics error: " + e.getMessage());
        }
    }

    // Ambil data player terbaru dari database (untuk refresh statistik)
    public Player getPlayerById(int id) {
        String sql = "SELECT * FROM players WHERE id = ?";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Player p = new Player(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("wins"),
                    rs.getInt("losses"),
                    rs.getInt("draws"),
                    rs.getInt("score")
                );
                conn.close();
                return p;
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Get player error: " + e.getMessage());
        }
        return null;
    }

    // Ambil Top 5 scorers dari database
    public ArrayList<Player> getTopFiveScorers() {
        ArrayList<Player> topPlayers = new ArrayList<>();
        String sql = "SELECT * FROM players ORDER BY score DESC, wins DESC LIMIT 5";
        try {
            Connection conn = DatabaseManager.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                topPlayers.add(new Player(
                    rs.getInt("id"),
                    rs.getString("username"),
                    rs.getInt("wins"),
                    rs.getInt("losses"),
                    rs.getInt("draws"),
                    rs.getInt("score")
                ));
            }
            conn.close();
        } catch (Exception e) {
            System.out.println("Get top scorers error: " + e.getMessage());
        }
        return topPlayers;
    }
}
