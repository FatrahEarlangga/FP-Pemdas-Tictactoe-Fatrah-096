import javax.swing.*;
import java.awt.*;

public class StatisticsFrame extends JFrame {
    private Player currentPlayer;
    private PlayerService playerService;

    public StatisticsFrame(Player player) {
        this.playerService = new PlayerService();
        // Refresh data terbaru dari DB
        Player refreshed = playerService.getPlayerById(player.getId());
        this.currentPlayer = (refreshed != null) ? refreshed : player;

        setTitle("Statistik - " + currentPlayer.getUsername());
        setSize(350, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 60));

        // Judul
        JLabel lblTitle = new JLabel("📊 Statistik Saya", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Panel statistik
        JPanel statsPanel = new JPanel(new GridLayout(6, 2, 10, 12));
        statsPanel.setBackground(new Color(40, 40, 80));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        addStatRow(statsPanel, "👤 Username:", currentPlayer.getUsername());
        addStatRow(statsPanel, "🏆 Total Skor:", String.valueOf(currentPlayer.getScore()));
        addStatRow(statsPanel, "✅ Menang:", String.valueOf(currentPlayer.getWins()));
        addStatRow(statsPanel, "❌ Kalah:", String.valueOf(currentPlayer.getLosses()));
        addStatRow(statsPanel, "🤝 Draw:", String.valueOf(currentPlayer.getDraws()));

        int totalGames = currentPlayer.getWins() + currentPlayer.getLosses() + currentPlayer.getDraws();
        addStatRow(statsPanel, "🎮 Total Game:", String.valueOf(totalGames));

        mainPanel.add(statsPanel, BorderLayout.CENTER);

        // Tombol tutup
        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(30, 30, 60));
        JButton btnClose = new JButton("Tutup");
        btnClose.setFont(new Font("Arial", Font.BOLD, 13));
        btnClose.setBackground(new Color(100, 80, 150));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFocusPainted(false);
        btnClose.addActionListener(e -> dispose());
        btnPanel.add(btnClose);
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void addStatRow(JPanel panel, String label, String value) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Arial", Font.PLAIN, 13));
        lbl.setForeground(new Color(180, 200, 255));

        JLabel val = new JLabel(value, SwingConstants.RIGHT);
        val.setFont(new Font("Arial", Font.BOLD, 13));
        val.setForeground(Color.WHITE);

        panel.add(lbl);
        panel.add(val);
    }
}
