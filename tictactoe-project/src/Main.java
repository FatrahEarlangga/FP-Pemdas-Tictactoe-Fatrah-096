import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                // Inisialisasi database PostgreSQL saat startup
                try {
                    DatabaseManager.initializeDatabase();
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null,
                            "Koneksi ke database PostgreSQL gagal!\n\n" +
                                    "Silakan periksa hal-hal berikut:\n" +
                                    "1. Apakah PostgreSQL Server Anda sudah aktif (running)?\n" +
                                    "2. Apakah Anda sudah membuat database bernama 'game_project'?\n" +
                                    "   (Query: CREATE DATABASE game_project;)\n" +
                                    "3. Apakah username ('postgres') dan password ('Erlangga') di DatabaseManager.java sudah benar?\n\n"
                                    +
                                    "Detail Error: " + e.getMessage(),
                            "Database Connection Error",
                            JOptionPane.ERROR_MESSAGE);
                }

                LoginFrame loginFrame = new LoginFrame();
                loginFrame.setVisible(true);
            }
        });
    }
}
