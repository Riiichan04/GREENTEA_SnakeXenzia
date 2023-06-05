package snake;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;

public class MainFrame extends JFrame {

    public MainFrame(){
        setIconImage(new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\snake\\img\\snakeIcon.jpg").getImage());
        setTitle("Snake Eater");
        setLayout(null);
        setResizable(false);
        setSize(600+14 + 425, 600+36);
        MainPanel mp = new MainPanel(600, 600);
        add(mp);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

}
