import javax.swing.*;
import java.awt.*;

public class MainMenuFrame extends JFrame {
    private Player currentPlayer;
    private JButton btnStartGame;
    private JButton btnStatistics;
    private JButton btnTopScorers;
    private JButton btnExit;

    public MainMenuFrame(Player player) {
        this.currentPlayer = player;
        setTitle("Tic-Tac-Toe - Main Menu");
        setSize(400, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 60));

        // Judul
        JLabel lblTitle = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 5, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Sambutan
        JLabel lblWelcome = new JLabel("Halo, " + currentPlayer.getUsername() + "!", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.ITALIC, 14));
        lblWelcome.setForeground(new Color(180, 200, 255));

        // Panel menu
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuPanel.setBackground(new Color(30, 30, 60));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(10, 60, 10, 60));
        menuPanel.add(lblWelcome);

        btnStartGame = createMenuButton("🎮  Mulai Game");
        btnStatistics = createMenuButton("📊  Statistik Saya");
        btnTopScorers = createMenuButton("🏆  Top 5 Skor");
        btnExit = createMenuButton("❌  Keluar");
        btnExit.setBackground(new Color(150, 50, 50));

        menuPanel.add(btnStartGame);
        menuPanel.add(btnStatistics);
        menuPanel.add(btnTopScorers);
        menuPanel.add(btnExit);

        mainPanel.add(menuPanel, BorderLayout.CENTER);

        // Event handlers
        btnStartGame.addActionListener(e -> {
            GameFrame gameFrame = new GameFrame(currentPlayer);
            gameFrame.setVisible(true);
            this.dispose();
        });

        btnStatistics.addActionListener(e -> {
            StatisticsFrame statsFrame = new StatisticsFrame(currentPlayer);
            statsFrame.setVisible(true);
        });

        btnTopScorers.addActionListener(e -> {
            TopScorersFrame topFrame = new TopScorersFrame();
            topFrame.setVisible(true);
        });

        btnExit.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin mau keluar?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });

        add(mainPanel);
    }

    private JButton createMenuButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(new Color(70, 100, 160));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        return btn;
    }
}
