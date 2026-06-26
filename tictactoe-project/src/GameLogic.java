import java.util.Random;

public class GameLogic {
    private char[] board;
    private Random random;

    public GameLogic() {
        board = new char[9];
        random = new Random();
        resetBoard();
    }

    // Reset papan ke kondisi awal
    public void resetBoard() {
        for (int i = 0; i < board.length; i++) {
            board[i] = ' ';
        }
    }

    // Lakukan move di index tertentu dengan simbol tertentu
    public boolean makeMove(int index, char symbol) {
        if (index < 0 || index >= 9) return false;
        if (board[index] != ' ') return false;
        board[index] = symbol;
        return true;
    }

    // Cek apakah simbol tertentu menang
    public boolean checkWinner(char symbol) {
        int[][] patterns = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // baris
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // kolom
            {0, 4, 8}, {2, 4, 6}              // diagonal
        };
        for (int[] pattern : patterns) {
            int a = pattern[0], b = pattern[1], c = pattern[2];
            if (board[a] == symbol && board[b] == symbol && board[c] == symbol) {
                return true;
            }
        }
        return false;
    }

    // Cek apakah papan penuh (draw)
    public boolean isDraw() {
        for (char c : board) {
            if (c == ' ') return false;
        }
        return true;
    }

    // Computer move: coba menang dulu, lalu block player, lalu random
    public int computerMove() {
        // 1. Coba cari langkah menang untuk komputer (O)
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = 'O';
                if (checkWinner('O')) {
                    board[i] = ' '; // kembalikan dulu, GameFrame yang update
                    return i;
                }
                board[i] = ' ';
            }
        }

        // 2. Block langkah menang player (X)
        for (int i = 0; i < 9; i++) {
            if (board[i] == ' ') {
                board[i] = 'X';
                if (checkWinner('X')) {
                    board[i] = ' ';
                    return i;
                }
                board[i] = ' ';
            }
        }

        // 3. Ambil tengah kalau masih kosong
        if (board[4] == ' ') return 4;

        // 4. Random move ke sel kosong
        int index;
        do {
            index = random.nextInt(9);
        } while (board[index] != ' ');
        return index;
    }

    public char[] getBoard() { return board; }
}
