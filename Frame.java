import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    private JButton[][] buttons; // Oyun tahtası için düğmeler
    private char currentPlayer; // Sıradaki oyuncu (X veya O)
    Game game;

    int[][] pattern ={{7,8,9}, {4,5,6}, {1,2,3}};

    public Frame() {
        game = new Game();
        setTitle("Tic Tac Toe");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        currentPlayer = 'X';

        initializeButtons();
        setVisible(true);
    }

    private void initializeButtons() {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                add(buttons[row][col]);
            }
        }
    }
    private class ButtonClickListener implements ActionListener {
        private int row;
        private int col;

        public ButtonClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {

                if(currentPlayer=='X'){
                    game.pl1.addMove(pattern[row][col]);
                }
                else{
                    game.pl2.addMove(pattern[row][col]);
                }

                for(int j = 0; j<3; j++){
                    for(int i= pattern[j][0]; i<=pattern[j][2]; i++){
                        if(game.pl1.doesContain(i)){
                            buttons[j][i-pattern[j][0]].setText(String.valueOf('X'));
                            buttons[j][i-pattern[j][0]].setEnabled(false);
                            if(i==game.pl1.hand[2] && currentPlayer !='X'){
                                buttons[j][i-pattern[j][0]].setFont(new Font("Arial", Font.PLAIN, 40));
                            }
                        }
                        else if(game.pl2.doesContain(i)){
                            buttons[j][i-pattern[j][0]].setText(String.valueOf('O'));
                            buttons[j][i-pattern[j][0]].setEnabled(false);
                            if(i==game.pl2.hand[2]&& currentPlayer !='O'){
                                buttons[j][i-pattern[j][0]].setFont(new Font("Arial", Font.PLAIN, 40));
                            }
                        }
                        else{
                            buttons[j][i-pattern[j][0]].setText("");
                            buttons[j][i-pattern[j][0]].setEnabled(true);
                        }
                    }
                }
                game.display();
                checkForWinner();
                if (currentPlayer == 'X') {
                    currentPlayer = 'O';
                } else {
                    currentPlayer = 'X';
                }

        }
        public void checkForWinner(){
            if(game.gameFinished()){
                System.out.println("oyun bitii");
                setVisible(false);
            }
        }
    }
}

