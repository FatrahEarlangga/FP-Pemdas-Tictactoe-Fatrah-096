import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private PlayerService playerService;

    public LoginFrame() {
        playerService = new PlayerService();
        setTitle("Tic-Tac-Toe - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 60));

        // Panel judul
        JLabel lblTitle = new JLabel("TIC-TAC-TOE", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        mainPanel.add(lblTitle, BorderLayout.NORTH);

        // Panel form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(30, 30, 60));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Username
        JLabel lblUsername = new JLabel("Username:");
        lblUsername.setForeground(Color.WHITE);
        lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(lblUsername, gbc);

        txtUsername = new JTextField(15);
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(txtUsername, gbc);

        // Password
        JLabel lblPassword = new JLabel("Password:");
        lblPassword.setForeground(Color.WHITE);
        lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(lblPassword, gbc);

        txtPassword = new JPasswordField(15);
        txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(txtPassword, gbc);

        mainPanel.add(formPanel, BorderLayout.CENTER);

        // Tombol login & register
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnPanel.setBackground(new Color(30, 30, 60));
        
        btnLogin = new JButton("LOGIN");
        btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
        btnLogin.setBackground(new Color(70, 130, 180));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setPreferredSize(new Dimension(110, 35));
        btnLogin.setFocusPainted(false);
        btnPanel.add(btnLogin);
        
        JButton btnRegister = new JButton("REGISTER");
        btnRegister.setFont(new Font("Arial", Font.BOLD, 14));
        btnRegister.setBackground(new Color(46, 139, 87));
        btnRegister.setForeground(Color.WHITE);
        btnRegister.setPreferredSize(new Dimension(110, 35));
        btnRegister.setFocusPainted(false);
        btnPanel.add(btnRegister);
        
        mainPanel.add(btnPanel, BorderLayout.SOUTH);

        // Supaya bisa login dengan Enter
        txtPassword.addActionListener(e -> doLogin());
        btnLogin.addActionListener(e -> doLogin());
        btnRegister.addActionListener(e -> doRegister());

        add(mainPanel);
    }

    private void doLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Player player = playerService.login(username, password);
            if (player != null) {
                JOptionPane.showMessageDialog(this, "Login berhasil! Selamat datang, " + player.getUsername() + "!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                MainMenuFrame menuFrame = new MainMenuFrame(player);
                menuFrame.setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
                txtPassword.setText("");
            }
        } catch (java.sql.SQLException e) {
            showDatabaseErrorDialog("Login Gagal", e);
        }
    }

    private void doRegister() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username dan password tidak boleh kosong untuk registrasi!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (username.length() < 3) {
            JOptionPane.showMessageDialog(this, "Username minimal harus 3 karakter!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            boolean success = playerService.register(username, password);
            if (success) {
                JOptionPane.showMessageDialog(this, "Registrasi berhasil! Silakan klik LOGIN untuk masuk ke game.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Username '" + username + "' sudah terdaftar!\nSilakan gunakan username lain.", "Registrasi Gagal", JOptionPane.WARNING_MESSAGE);
            }
        } catch (java.sql.SQLException e) {
            showDatabaseErrorDialog("Registrasi Gagal", e);
        }
    }

    private void showDatabaseErrorDialog(String title, java.sql.SQLException e) {
        JOptionPane.showMessageDialog(this,
            "Gagal terhubung ke database PostgreSQL!\n\n" +
            "Silakan periksa hal-hal berikut:\n" +
            "1. Apakah PostgreSQL Server sudah aktif (running)?\n" +
            "2. Apakah Anda sudah membuat database bernama 'game_project'?\n" +
            "   (Query: CREATE DATABASE game_project;)\n" +
            "3. Apakah username ('postgres') dan password ('Erlangga') di DatabaseManager.java sudah benar?\n\n" +
            "Detail Error: " + e.getMessage(),
            title + " - Database Error",
            JOptionPane.ERROR_MESSAGE);
    }
}
