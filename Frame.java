import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame extends JFrame {
    private JButton[][] buttons; // for game desk, buttons
    private char currentPlayer; // Next player (X or O)
    Game game;
    public static int moveAmount = 5;

    JSlider redSlider;
    JSlider greenSlider;
    JSlider blueSlider;
    int initialColor = 180;
    int red = initialColor; int blue= initialColor; int green= initialColor;
    JSlider amountSlider;
    JMenuBar menubar = new JMenuBar();
    JMenu settings = new JMenu("Settings");
    JMenuItem setColor = new JMenuItem("Set Color");
    JMenuItem setTurnAmount = new JMenuItem(("Choose movement amount"));

    int[][] pattern ={{7,8,9}, {4,5,6}, {1,2,3}};

    public Frame() {
        gameInitializer();
    }
    public void gameInitializer(){
        game = new Game();
        getContentPane().removeAll();
        setTitle("Tic Tac Toe");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 3));

        setLocation(400,150 );

        buttons = new JButton[3][3];
        currentPlayer = 'X';
        ActionListener lislis = new colorListenere();
        ActionListener turnn = new turnAmount();
        settings.add(setColor);
        settings.add(setTurnAmount);

        setColor.addActionListener(lislis);
        setTurnAmount.addActionListener(turnn);
        menubar.add(settings);
        setJMenuBar(menubar);

        initializeButtons();
        setVisible(true);
    }

    private void initializeButtons() {
        String greet = "1V1      ";
        
        int count= 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {

                buttons[row][col] = new JButton();
                buttons[row][col].setFont(new Font("Arial", Font.BOLD, 70));
                buttons[row][col].setBackground(new Color(red,green,blue)); //this can be opened
                if(greet.length()>count){
                    if(row ==2 && col ==1){ //the location of button that shows movement amount
                        buttons[row][col].setFont(new Font("Arial", Font.BOLD, 40));
                        buttons[row][col].setText(""+moveAmount + " move");    
                    }
                    else{
                        buttons[row][col].setText(""+greet.charAt(count));
                    }
                }
                buttons[row][col].setFocusable(false);
                buttons[row][col].setForeground(Color.RED);
                buttons[row][col].addActionListener(new ButtonClickListener(row, col));
                add(buttons[row][col]);
                count++;
            }
        }
    }
    public int buttonCounter(){
        int count= 0;
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if(buttons[row][col].isEnabled()){
                    count++;
                }
            }
        }
        return count;
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
                        buttons[j][i-pattern[j][0]].setFont(new Font("Arial", Font.BOLD, 70));

                        if(i==game.pl1.hand[moveAmount-1] && currentPlayer !='X'){
                            buttons[j][i-pattern[j][0]].setFont(new Font("Arial", Font.PLAIN, 40));
                        }
                    }
                    else if(game.pl2.doesContain(i)){
                        buttons[j][i-pattern[j][0]].setText(String.valueOf('O'));
                        buttons[j][i-pattern[j][0]].setEnabled(false);
                        buttons[j][i-pattern[j][0]].setFont(new Font("Arial", Font.BOLD, 70));

                        if(i==game.pl2.hand[moveAmount-1]&& currentPlayer !='O'){
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
    }class turnAmount implements ActionListener{
        public void actionPerformed(ActionEvent e){
            JFrame frame;
            JPanel contentPanel;


            frame = new JFrame("Movement Adjustment App");
            frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            frame.setSize(400, 300);


            contentPanel = new JPanel();
            contentPanel.setLayout(new BorderLayout());

            // Create sliders
            amountSlider = createSlider("Amount", 3, 5, 5,1);

            // Add sliders to content panel
            ChangeListener lis = new mylistenerAmount();
            amountSlider.addChangeListener(lis);

            contentPanel.add(amountSlider, BorderLayout.CENTER);
            frame.add(contentPanel);
            frame.setVisible(true);
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
            redSlider = createSlider("Red", 0, 255, initialColor, 50);
            greenSlider = createSlider("Green", 0, 255, initialColor, 50);
            blueSlider = createSlider("Blue", 0, 255, initialColor, 50);

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
            update();
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
            update();
        }
    }
    class mylistenerAmount implements ChangeListener {
        public void stateChanged(ChangeEvent e){
            updateAmount();
        }
    }
    private void updateAmount() {
        moveAmount = amountSlider.getValue();
        gameInitializer();
    }
    private void update() {
        red = redSlider.getValue();
        green = greenSlider.getValue();
        blue = blueSlider.getValue();
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setBackground(new Color(red,green,blue));
            }
        }
    }
    private JSlider createSlider(String label, int min, int max, int initialValue, int gap) {
        JSlider slider = new JSlider(min, max, initialValue);
        slider.setMajorTickSpacing(gap);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setBorder(BorderFactory.createTitledBorder(label));
        return slider;
    }
    public void checkForWinner(){
        if(game.gameFinished() || buttonCounter()==0){
            String title = "";
            if(game.pl1.didFinish()){
                title = "Winner is Player 1!!!";
            }
            else if(game.pl2.didFinish()){
                title = "Winner is Player 2!!!";
            }
            else{
                title = "Draw!!!";
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

