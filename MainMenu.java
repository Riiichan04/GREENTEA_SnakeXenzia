package snake;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.Scanner;

public class MainMenu extends JFrame {
    JLayeredPane layer = new JLayeredPane();
    public MainMenu() {
        setSize(600, 600);
        setLayout(null);
        layer.setBounds(0, 0, this.getWidth(), this.getHeight());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);
        setIconImage(new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\snake\\img\\snakeIcon.jpg").getImage());
        setTitle("Snake Eater");

        setUpIconGame();
        setUpPlayButton();

        ImageIcon imgBG = new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\imageIcon\\gameBG2.jpg");
        Image imageBG = imgBG.getImage().getScaledInstance(600, 600, Image.SCALE_SMOOTH);
        imgBG = new ImageIcon(imageBG);

        JLabel background = new JLabel(imgBG);
        background.setBounds(0, 0, this.getWidth(), this.getHeight());
        layer.add(background, JLayeredPane.DEFAULT_LAYER);

        this.add(layer);
        setVisible(true);
    }

    public void setUpIconGame() {
        JLabel nameGameLabel = new JLabel("Snake Eater");
        nameGameLabel.setBounds(225, 150, 200, 50);
        nameGameLabel.setFont(new Font("Nunito", Font.BOLD, 25));
        nameGameLabel.setForeground(Color.PINK);
        layer.add(nameGameLabel, JLayeredPane.POPUP_LAYER);
    }
    public void setUpPlayButton() {
        JButton playBTN = new JButton("Chơi");
        playBTN.setBounds(200, 400, 200, 50);
        playBTN.setFocusable(false);
        playBTN.setFont(new Font("Droid Sans Mono", Font.BOLD, 22));
        playBTN.setBackground(Color.CYAN);
        playBTN.addActionListener(e -> {
            MainFrame mf = new MainFrame();
            this.dispose();
        });
        layer.add(playBTN, JLayeredPane.POPUP_LAYER);
    }

    public static void main(String[] args) {
        MainMenu mm = new MainMenu();
    }
}
