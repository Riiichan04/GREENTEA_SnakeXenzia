package snake;

import com.sun.tools.javac.Main;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.security.Key;
import java.util.Random;
public class MainPanel extends JPanel {
    private int PANEL_HEIGHT;
    private int PANEL_WIDTH;
    private int UNIT_SIZE = 30;
    private int[] x; //Vì độ dài tối đa của rắn = diện tích của cả panel (600*600/30)
    private int[] y; //Vì độ dài tối đa tọa độ của rắn = diện tích của cả panel (600*600/30)
    private boolean isRunning = false;
    private final Random random = new Random();
    private Timer time;
    private int appleX;
    private int appleY;
    private int defaultLengthSnake = 4;
    private int DELAY = 200;
    private char direction = 'E';
    private int countApple = 0;
    private int appleID = random.nextInt(3) + 1;
    private int countLength = 0;
    private int score = 0;
    private int countEaten = 0;
    private int countA1 = 0, countA2 = 0, countA3 = 0;
    private final ImageOperation img = new ImageOperation(UNIT_SIZE);
    private final SnakeOperation snOp = new SnakeOperation();
    JLabel scoreLabel = new JLabel("",JLabel.CENTER);
    JLabel totalApple = new JLabel("Số táo ăn được: " + countEaten);
    JLabel totalA1 = new JLabel("Số táo bị cắn gần hết đã ăn: " + countA1);
    JLabel totalA2 = new JLabel("Số táo bị cắn một tí đã ăn: " + countA2);
    JLabel totalA3 = new JLabel("Số táo còn nguyên vẹn đã ăn: " + countA3);
    JLabel totalLength = new JLabel("Độ dài hiện tại của rắn là: " + defaultLengthSnake);
    JButton pauseButton = new JButton("Pause");
    boolean isPause = false;
    JButton buttonLose = new JButton("Chơi Lại");
    public MainPanel(int PANEL_WIDTH, int PANEL_HEIGHT) {
        pauseButton.setEnabled(true);
        this.PANEL_WIDTH = PANEL_WIDTH;
        this.PANEL_HEIGHT = PANEL_HEIGHT;
        x = new int[this.PANEL_WIDTH * this.PANEL_HEIGHT/UNIT_SIZE];
        y = new int[this.PANEL_HEIGHT * this.PANEL_WIDTH/UNIT_SIZE];

        setLayout(null);
        setBounds(0, 0, this.PANEL_WIDTH + 450, this.PANEL_HEIGHT);
        setFocusable(true);

        SetupKey setupKey = new SetupKey();
        addKeyListener(setupKey);

        InfoGamePanel igp = new InfoGamePanel();
        add(igp);
        startGame();
    }
    public void startGame() {
        snOp.newApple();
        isRunning = true;
        time = new Timer(DELAY, actionListener());

        time.start();
    }
    public String toString(int[] arr) {
        StringBuilder string = new StringBuilder();
        for (int element : arr) {
            string.append(element).append(" ");
        }
        return string.toString();
    }

    public void draw(Graphics g) {
        if (isRunning) {
            //Vẽ background
            g.drawImage(img.getBackground().getImage(),0, 0, PANEL_WIDTH, PANEL_HEIGHT, this);
            //Vẽ trái táo
            g.drawImage(img.getImg(appleID), appleX, appleY, UNIT_SIZE, UNIT_SIZE, this);
            //Vẽ đầu rắn
            g.drawImage(img.getSnakeHead(direction), x[0], y[0], UNIT_SIZE, UNIT_SIZE, null);
            //Vẽ thân rắn
            for (int i = 1; i < defaultLengthSnake; i++) {
                g.setColor(new Color(255, 89, 0));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
            }
        }
        else {
            g.clearRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
            ImageIcon bgLose = new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\imageIcon\\winBG.jpg");
            Image imgLose = bgLose.getImage().getScaledInstance(PANEL_WIDTH, PANEL_HEIGHT, Image.SCALE_SMOOTH);
            bgLose = new ImageIcon(imgLose);
            g.drawImage(bgLose.getImage(), 0, 0, PANEL_WIDTH, PANEL_HEIGHT, this);
        }
    }
    public ActionListener actionListener() {
        return actionEvent -> {
            if (isRunning) {
                snOp.snakeMove();
                snOp.isEaten();
                snOp.checkLose();
                snOp.checkOutScreen();
                snOp.eventWhenOut();
            }
            repaint();
        };
    }
    public class SnakeOperation {
        public void snakeMove() {
            //Rắn di chuyển
            //Di chuyển từ cuối lên đầu
            for (int i = defaultLengthSnake; i > 0; i--) {
                x[i] = x[i - 1];
                y[i] = y[i - 1];
            }
            switch (direction) {
                case 'N' -> y[0] = (y[0] - UNIT_SIZE);
                case 'S' -> y[0] = (y[0] + UNIT_SIZE);
                case 'W' -> x[0] = (x[0] - UNIT_SIZE);
                case 'E' -> x[0] = (x[0] + UNIT_SIZE);
            }
        }
        //Kiểm tra rắn đã ăn táo chưa
        public void isEaten() {
            if ((x[0] == appleX) && (y[0] == appleY)) {
                countEaten++;
                countApple += appleID;
                if (countApple > 2) {
                    defaultLengthSnake++;
                    countApple -= 2;
                }
                newApple();
                score += appleID;
                scoreLabel.setText("Điểm: " + score);
                switch (appleID) {
                    case 1 -> countA1++;
                    case 2 -> countA2++;
                    case 3 -> countA3++;
                }
                totalApple.setText("Số táo ăn được: " + countEaten);
                totalA1.setText("Số táo bị cắn gần hết đã ăn: " + countA1);
                totalA2.setText("Số táo bị cắn một tí đã ăn: " + countA2);
                totalA3.setText("Số táo còn nguyên vẹn đã ăn: " + countA3);
                totalLength.setText("Độ dài hiện tại của rắn là: " + defaultLengthSnake);
            }
        }

        //Sự kiện xảy ra khi rắn đi ra khỏi màn hình
        public void eventWhenOut() {

            //Đi ra màn hình bên phải
            if (x[0] > PANEL_WIDTH - UNIT_SIZE) {
                x[countLength++] = 0;
            }
            //Đi ra màn hình bên trái
            else if (x[0] < 0) {
                x[countLength++] = PANEL_WIDTH - UNIT_SIZE;
            }
            //Đi ra màn hình bên trên
            else if (y[0] < 0) {
                y[countLength++] = PANEL_HEIGHT - UNIT_SIZE;
            }
            //Đi ra màn hình bên dưới
            else if (y[0] > PANEL_HEIGHT - UNIT_SIZE) {
                y[countLength++] = 0;
            }
            //Rắn đã ở trong màn hình -> reset bộ đếm rắn
            else countLength = 0;
        }
        public void newApple() {
            appleID = random.nextInt(3) + 1;
            appleX = random.nextInt(PANEL_WIDTH / UNIT_SIZE) * UNIT_SIZE;
            appleY = random.nextInt(PANEL_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        }
        public boolean checkOutScreen() {
            return (x[0] + UNIT_SIZE > PANEL_WIDTH && direction == 'E') || (x[0] < 0 && direction == 'W') || (y[0] + UNIT_SIZE > PANEL_HEIGHT && direction == 'S') || (y[0] < 0 && direction == 'N');
        }
        //Kiểm tra thua khi nào
        public void checkLose() {
            //Nếu đầu chạm vào cơ thể của nó
            for (int i = defaultLengthSnake; i > 0; i--) {
                if ((x[0] == (x[i])) && (y[0] == (y[i]))) {
                    isRunning = false;
                    break;
                }
            }
            if (!isRunning) {
                time.stop();
                pauseButton.setEnabled(false);

                
                buttonLose.setBounds(200, 250, 200, 50);
                buttonLose.setFocusable(false);
                buttonLose.setBackground(Color.CYAN);
                buttonLose.setFont(new Font("Nunito", Font.BOLD, 20));
                buttonLose.addActionListener(e -> {
                    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor((JPanel) getParent());
                    MainFrame mf = new MainFrame();
                    frame.dispose();
                });
                add(buttonLose);

                JLabel loseLabel = new JLabel("Bạn đã thất bại");
                JLabel scoreGained = new JLabel("Bạn nhận được: " + score + " điểm");
                loseLabel.setBounds(200, 50, 300, 50);
                scoreGained.setBounds(200, 100, 300, 50);
                loseLabel.setFont(new Font("Droid Sans Mono", Font.BOLD, 30));
                scoreGained.setFont(new Font("Segoe UI", Font.BOLD, 20));
                loseLabel.setForeground(Color.ORANGE);

                add(loseLabel);
                add(scoreGained);
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
//        super.paintComponent(g);    //Khi thua thì màn hình trắng (Vì khi timer stop sẽ vẽ lại hàm draw -> null)
        draw(g);
    }

    public class SetupKey extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            if (!snOp.checkOutScreen() && !isPause) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT, KeyEvent.VK_A -> {
                        if (direction != 'E') {
                            direction = 'W';
                        }
                    }
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D -> {
                        if (direction != 'W') {
                            direction = 'E';
                        }
                    }
                    case KeyEvent.VK_UP, KeyEvent.VK_W -> {
                        if (direction != 'S') {
                            direction = 'N';
                        }
                    }
                    case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                        if (direction != 'N') {
                            direction = 'S';
                        }

                    }
                }
            }
            //Rắn đi nhanh gấp 4 lần so với bình thường
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                time.setDelay(DELAY/4);
            }
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                if (!isPause) {
                    time.stop();
                    isPause = true;
                }
                else {
                    time.start();
                    isPause = false;
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_SHIFT) {
                time.setDelay(DELAY);
            }
        }
    }

    public class InfoGamePanel extends JPanel {

        JButton menuButton = new JButton("Main Menu");
        public InfoGamePanel() {
            this.setBounds(600, 0, 1024 - PANEL_WIDTH, PANEL_HEIGHT);
            this.setLayout(null);
            this.setBackground(Color.WHITE);

            ImageIcon bgImg = new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\imageIcon\\gameBG3.jpg");
            Image img = bgImg.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
            bgImg = new ImageIcon(img);

            JLabel background = new JLabel(bgImg);
            background.setBounds(0, 0, this.getWidth(), this.getHeight());

            JLabel nameGame = new JLabel("Snake Eater", JLabel.CENTER);
            nameGame.setBounds(0, 25, this.getWidth(), 40);
            nameGame.setFont(new Font("Verdana", Font.BOLD, 25));
            nameGame.setForeground(new Color(255, 89, 0));

            scoreLabel.setText("Điểm: " + score);
            scoreLabel.setBounds(0, 80, this.getWidth(), 40);
            scoreLabel.setFont(new Font("Nunito", Font.BOLD, 20));
            scoreLabel.setForeground(Color.CYAN);

            JPanel howToPlayPanel = new JPanel();
            howToPlayPanel.setBounds(0, 160, this.getWidth(), 225);
            howToPlayPanel.setLayout(new GridLayout(1, 2));
            TitledBorder titledBorder = new TitledBorder(new LineBorder(Color.BLACK, 2), "HƯỚNG DẪN", 0, 0, new Font(Font.SANS_SERIF, Font.BOLD, 20), new Color(28, 213, 255));
            howToPlayPanel.setBorder(titledBorder);

            //Cách set Background của JTextField và JTextArea trong suốt:
                //B1. setOpaque(false) và setBackground(new Color(0,0,0,0)) cho Container của JTextField và JTextArea
            howToPlayPanel.setOpaque(false);
            howToPlayPanel.setBackground(new Color(0, 0, 0, 0));

                //B2. setBackground(null) cho JTextField hay JTextArea cần background trong suốt
            JTextArea howToKey = new JTextArea();
            howToKey.setBackground(null);
            howToKey.setEditable(false);    //Để user không chỉnh sửa được nội dung của nó
            howToKey.setFocusable(false);   //Để user không thể bôi đen được (<=> user-select: none của CSS)
            howToKey.setText("\nW, Arrow Up: Đi Lên\nA, Arrow Left: Rẽ Trái\nS, Arrow Down: Đi Xuống\nD, Arrow Right: Rẽ Phải\nNhấn giữ phím Shift để rắn\n   di chuyển nhanh hơn\nESC hoặc nút Pause \n   để tạm ngừng");
            howToKey.setFont(new Font(Font.SANS_SERIF, Font.ITALIC + Font.BOLD, 14));
            howToKey.setForeground(new Color(255, 255, 255));
            //Tạo Right-Border
            howToKey.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.BLACK));

            JTextArea howToScore = new JTextArea();
            howToScore.setBackground(null);
            howToScore.setEditable(false);
            howToKey.setFocusable(false);
            howToScore.setText("\n Có 3 loại táo tương ứng với \n    nhận 1, 2, 3 điểm khi ăn.\n Bạn sẽ thua nếu cố để rắn tự\n     ăn bản thân mình vì bạn\n  KHÔNG PHẢI OUROBOROS!");
            howToScore.setFont(new Font(Font.SANS_SERIF, Font.ITALIC + Font.BOLD, 14));
            howToScore.setForeground(new Color(255, 255, 255));

            howToPlayPanel.add(howToKey); howToPlayPanel.add(howToScore);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setBounds(0, 550, this.getWidth(), 100);
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            //Set background trong suốt để nhìn thấy background ngoài sau
            buttonPanel.setOpaque(false);
            buttonPanel.setBackground(new Color(0, 0, 0, 0));

            pauseButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            menuButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
            pauseButton.setBackground(Color.CYAN);
            menuButton.setBackground(Color.CYAN);
            pauseButton.setFocusable(false);
            menuButton.setFocusable(false);

            buttonPanel.add(pauseButton); buttonPanel.add(menuButton);

            JPanel statisticPanel = new JPanel();
            statisticPanel.setBounds(0, 400, this.getWidth(), 150);
            statisticPanel.setLayout(new BoxLayout(statisticPanel, BoxLayout.Y_AXIS));
            //Set background trong suốt để nhìn thấy background ngoài sau
            statisticPanel.setOpaque(false);
            statisticPanel.setBackground(new Color(0, 0, 0, 0));
            TitledBorder statisBoder = new TitledBorder(new LineBorder(Color.BLACK, 2), "THÔNG SỐ", 0, 0, new Font(Font.SANS_SERIF, Font.BOLD, 20), new Color(28, 213, 255));
            statisticPanel.setBorder(statisBoder);

            totalApple.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            totalA1.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            totalA2.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            totalA3.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));
            totalLength.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));

            totalApple.setForeground(Color.GREEN);
            totalA1.setForeground(new Color(0, 255, 149));
            totalA2.setForeground(new Color(0, 255, 111));
            totalA3.setForeground(new Color(174, 255, 32));
            totalLength.setForeground(new Color(255, 221, 0));

            statisticPanel.add(totalApple);
            statisticPanel.add(totalA1);
            statisticPanel.add(totalA2);
            statisticPanel.add(totalA3);
            statisticPanel.add(totalLength);

            JLayeredPane layer = new JLayeredPane();
            layer.setBounds(0, 0, this.getWidth(), this.getHeight());
            layer.add(nameGame, JLayeredPane.POPUP_LAYER);
            layer.add(scoreLabel, JLayeredPane.POPUP_LAYER);
            layer.add(howToPlayPanel, JLayeredPane.POPUP_LAYER);
            layer.add(buttonPanel, JLayeredPane.POPUP_LAYER);
            layer.add(statisticPanel, JLayeredPane.POPUP_LAYER);
            layer.add(background, JLayeredPane.DEFAULT_LAYER);

            addEventButton();
            this.add(layer);
        }

        public void addEventButton() {
            pauseButton.addActionListener(e -> {
                if (!isPause) {
                    time.stop();
                    isPause = true;
                }
                else {
                    time.start();
                    isPause = false;
                }
            });
            menuButton.addActionListener(e -> {
                JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
                MainMenu mm = new MainMenu();
                frame.dispose();
            });
        }
    }

}
