import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;

public class TopScorersFrame extends JFrame {
    private JTable table;
    private PlayerService playerService;

    public TopScorersFrame() {
        playerService = new PlayerService();

        setTitle("Top 5 Scorers");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 60));

        // Judul
        JLabel lblTitle = new JLabel("🏆 Top 5 Scorers", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Buat tabel
        String[] columns = {"#", "Username", "Menang", "Kalah", "Draw", "Skor"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        // Ambil data dari database
        ArrayList<Player> topPlayers = playerService.getTopFiveScorers();
        int rank = 1;
        for (Player p : topPlayers) {
            String rankStr = rank == 1 ? "🥇" : rank == 2 ? "🥈" : rank == 3 ? "🥉" : String.valueOf(rank);
            model.addRow(new Object[]{rankStr, p.getUsername(), p.getWins(), p.getLosses(), p.getDraws(), p.getScore()});
            rank++;
        }

        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setBackground(new Color(40, 40, 80));
        table.setForeground(Color.WHITE);
        table.setGridColor(new Color(70, 70, 120));
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.getTableHeader().setBackground(new Color(50, 80, 140));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(80, 100, 180));

        // Center align semua kolom
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < columns.length; i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));
        scrollPane.getViewport().setBackground(new Color(40, 40, 80));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

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
}
