import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    private JButton[][] buttons; // Oyun tahtası için düğmeler
    private char currentPlayer; // Sıradaki oyuncu (X veya O)
    Game game;


    JSlider redSlider;
    JSlider greenSlider;
    JSlider blueSlider;

    JMenuBar menubar = new JMenuBar();
    JMenu colorM = new JMenu("Color");
    JMenuItem setColor = new JMenuItem("Set Color");

    int[][] pattern ={{7,8,9}, {4,5,6}, {1,2,3}};

    public Frame() {
        gameInitializer();
    }
    public void gameInitializer(){
        game = new Game();
        getContentPane().removeAll();
        setTitle("Tic Tac Toe");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        buttons = new JButton[3][3];
        currentPlayer = 'X';
        ActionListener lislis = new colorListenere();
        colorM.add(setColor);

        setColor.addActionListener(lislis);
        menubar.add(colorM);
        setJMenuBar(menubar);

        initializeButtons();
        setVisible(true);
    }

    private void initializeButtons() {

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[row][col].setBackground(new Color(79,110,50));
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
                        buttons[j][i-pattern[j][0]].setFont(new Font("Arial", Font.BOLD, 60));

                        if(i==game.pl1.hand[2] && currentPlayer !='X'){
                            buttons[j][i-pattern[j][0]].setFont(new Font("Arial", Font.PLAIN, 40));
                        }
                    }
                    else if(game.pl2.doesContain(i)){
                        buttons[j][i-pattern[j][0]].setText(String.valueOf('O'));
                        buttons[j][i-pattern[j][0]].setEnabled(false);
                        buttons[j][i-pattern[j][0]].setFont(new Font("Arial", Font.BOLD, 60));

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
    }
    class colorListenere implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JFrame frame;
            JPanel contentPanel;
            JMenuBar menuBar;
            JMenu colorMenu;

            frame = new JFrame("Color Adjustment App");
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setSize(400, 300);

            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());

            // Create sliders
            redSlider = createSlider("Red", 0, 255, 128);
            greenSlider = createSlider("Green", 0, 255, 128);
            blueSlider = createSlider("Blue", 0, 255, 128);

            // Add sliders to content panel
            contentPanel.add(redSlider, BorderLayout.NORTH);
            contentPanel.add(greenSlider, BorderLayout.CENTER);
            contentPanel.add(blueSlider, BorderLayout.SOUTH);

            // Create menu bar
            menuBar = new JMenuBar();
            colorMenu = new JMenu("Color");
            menuBar.add(colorMenu);

            // Add content panel to frame
            frame.setJMenuBar(menuBar);
            frame.add(contentPanel);

            // Update background color when sliders change
            updateBackgroundColor();
            ChangeListener lis = new mylistener();

            // Attach listeners to sliders
            redSlider.addChangeListener(lis);
            greenSlider.addChangeListener(lis);
            blueSlider.addChangeListener(lis);

            frame.setVisible(true);
        }
    }
    class mylistener implements ChangeListener {
        public void stateChanged(ChangeEvent e){
            updateBackgroundColor();
        }
    }
    private void updateBackgroundColor() {
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setBackground(new Color(red,green,blue));
            }
        }
    }
    private JSlider createSlider(String label, int min, int max, int initialValue) {
        JSlider slider = new JSlider(min, max, initialValue);
        slider.setMajorTickSpacing(50);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBorder(BorderFactory.createTitledBorder(label));
        return slider;
    }
    public void checkForWinner(){
        if(game.gameFinished()){
            String title = "";
            if(game.pl1.didFinish()){
                title = "Winner is Player 1!!!";
            }
            else{
                title = "Winner is Player 2!!!";
            }
            System.out.println("oyun bitii");

            int cevap = JOptionPane.showConfirmDialog(
                    null,  "Do you want to play again?", title,
                    JOptionPane.YES_NO_OPTION
            );

            if (cevap == JOptionPane.YES_OPTION) {
                gameInitializer();
            } else {
                System.out.println("Exiting...");
                System.exit(0);
            }
        }
    }

}

