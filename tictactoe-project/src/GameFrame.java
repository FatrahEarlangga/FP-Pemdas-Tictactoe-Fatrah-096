import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {
    private Player currentPlayer;
    private PlayerService playerService;
    private GameLogic gameLogic;
    private JButton[] buttons;
    private JLabel lblStatus;
    private JButton btnBack;
    private boolean gameOver;

    public GameFrame(Player player) {
        this.currentPlayer = player;
        this.playerService = new PlayerService();
        this.gameLogic = new GameLogic();
        this.gameOver = false;

        setTitle("Tic-Tac-Toe - Game");
        setSize(420, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 60));

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 30, 60));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        JLabel lblTitle = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle, BorderLayout.NORTH);

        lblStatus = new JLabel("Giliran kamu! (X)", SwingConstants.CENTER);
        lblStatus.setFont(new Font("Arial", Font.PLAIN, 14));
        lblStatus.setForeground(new Color(150, 220, 150));
        headerPanel.add(lblStatus, BorderLayout.SOUTH);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Board 3x3
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        boardPanel.setBackground(new Color(10, 10, 40));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            buttons[i].setBackground(new Color(50, 50, 100));
            buttons[i].setForeground(Color.WHITE);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorderPainted(false);
            buttons[i].setOpaque(true);
            final int index = i;
            buttons[i].addActionListener(e -> handlePlayerMove(index));
            boardPanel.add(buttons[i]);
        }

        mainPanel.add(boardPanel, BorderLayout.CENTER);

        // Tombol kembali
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(30, 30, 60));
        btnBack = new JButton("Kembali ke Menu");
        btnBack.setFont(new Font("Arial", Font.BOLD, 13));
        btnBack.setBackground(new Color(100, 80, 150));
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> goToMenu());
        bottomPanel.add(btnBack);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void handlePlayerMove(int index) {
        if (gameOver) return;

        // Coba move player (X)
        boolean moved = gameLogic.makeMove(index, 'X');
        if (!moved) {
            JOptionPane.showMessageDialog(this, "Sel sudah terisi! Pilih sel lain.", "Move Tidak Valid", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Update tampilan tombol
        buttons[index].setText("X");
        buttons[index].setForeground(new Color(100, 200, 255));

        // Cek player menang
        if (gameLogic.checkWinner('X')) {
            finishGame("WIN");
            return;
        }

        // Cek draw setelah move player
        if (gameLogic.isDraw()) {
            finishGame("DRAW");
            return;
        }

        // Giliran komputer (O)
        lblStatus.setText("Komputer sedang berpikir...");
        lblStatus.setForeground(new Color(255, 200, 100));

        // Pakai Timer supaya ada delay sedikit (UX lebih natural)
        javax.swing.Timer timer = new javax.swing.Timer(400, e -> {
            int compIndex = gameLogic.computerMove();
            gameLogic.makeMove(compIndex, 'O');
            buttons[compIndex].setText("O");
            buttons[compIndex].setForeground(new Color(255, 100, 100));

            // Cek komputer menang
            if (gameLogic.checkWinner('O')) {
                finishGame("LOSE");
                return;
            }

            // Cek draw setelah move komputer
            if (gameLogic.isDraw()) {
                finishGame("DRAW");
                return;
            }

            lblStatus.setText("Giliran kamu! (X)");
            lblStatus.setForeground(new Color(150, 220, 150));
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void finishGame(String result) {
        gameOver = true;

        // Update database
        playerService.updateStatistics(currentPlayer, result);

        String message;
        Color statusColor;
        if (result.equals("WIN")) {
            message = "🎉 Selamat! Kamu MENANG! (+10 poin)";
            statusColor = new Color(100, 255, 100);
        } else if (result.equals("LOSE")) {
            message = "😢 Kamu KALAH! Coba lagi!";
            statusColor = new Color(255, 100, 100);
        } else {
            message = "🤝 DRAW! (+3 poin)";
            statusColor = new Color(255, 220, 100);
        }

        lblStatus.setText(message);
        lblStatus.setForeground(statusColor);

        // Nonaktifkan semua tombol board
        for (JButton btn : buttons) {
            btn.setEnabled(false);
        }

        int choice = JOptionPane.showConfirmDialog(this,
            message + "\n\nMau main lagi?",
            "Game Selesai",
            JOptionPane.YES_NO_OPTION
        );

        if (choice == JOptionPane.YES_OPTION) {
            // Reset game
            gameLogic.resetBoard();
            gameOver = false;
            for (JButton btn : buttons) {
                btn.setText("");
                btn.setEnabled(true);
            }
            lblStatus.setText("Giliran kamu! (X)");
            lblStatus.setForeground(new Color(150, 220, 150));
        } else {
            goToMenu();
        }
    }

    private void goToMenu() {
        // Refresh data player dari DB sebelum balik ke menu
        PlayerService ps = new PlayerService();
        Player updatedPlayer = ps.getPlayerById(currentPlayer.getId());
        if (updatedPlayer == null) updatedPlayer = currentPlayer;

        MainMenuFrame menuFrame = new MainMenuFrame(updatedPlayer);
        menuFrame.setVisible(true);
        this.dispose();
    }
}
