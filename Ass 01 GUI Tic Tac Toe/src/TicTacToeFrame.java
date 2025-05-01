import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TicTacToeFrame extends JFrame {
    private TicTacToeButton[][] buttons = new TicTacToeButton[3][3];
    private char currentPlayer = 'X';
    private int moves = 0;

    public TicTacToeFrame() {
        setTitle("Tic Tac Toe GUI");
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel board = new JPanel(new GridLayout(3, 3));
        ButtonListener listener = new ButtonListener();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new TicTacToeButton(row, col);
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[row][col].addActionListener(listener);
                board.add(buttons[row][col]);
            }
        }

        JButton quit = new JButton("Quit");
        quit.setFont(new Font("Arial", Font.PLAIN, 20));
        quit.addActionListener(e -> System.exit(0));

        add(board, BorderLayout.CENTER);
        add(quit, BorderLayout.SOUTH);

        setSize(400, 450);
        setVisible(true);
    }

    private class ButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton btn = (TicTacToeButton) e.getSource();

            if (!btn.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Illegal move! Try again.");
                return;
            }

            btn.setText(String.valueOf(currentPlayer));
            moves++;

            if (checkWin()) {
                JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                promptNewGame();
            } else if (moves == 9) {
                JOptionPane.showMessageDialog(null, "It's a tie!");
                promptNewGame();
            } else {
                currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
            }
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (equals(buttons[i][0], buttons[i][1], buttons[i][2]) ||
                    equals(buttons[0][i], buttons[1][i], buttons[2][i]))
                return true;
        }
        return equals(buttons[0][0], buttons[1][1], buttons[2][2]) ||
                equals(buttons[0][2], buttons[1][1], buttons[2][0]);
    }

    private boolean equals(TicTacToeButton b1, TicTacToeButton b2, TicTacToeButton b3) {
        return !b1.getText().equals("") &&
                b1.getText().equals(b2.getText()) &&
                b2.getText().equals(b3.getText());
    }

    private void promptNewGame() {
        int choice = JOptionPane.showConfirmDialog(null, "Play again?", "New Game", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            for (TicTacToeButton[] row : buttons)
                for (TicTacToeButton b : row)
                    b.setText("");
            moves = 0;
            currentPlayer = 'X';
        } else {
            System.exit(0);
        }
    }
}
