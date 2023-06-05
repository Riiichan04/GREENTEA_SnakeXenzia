package snake;

import javax.swing.*;
import java.awt.*;

public class ImageOperation {
    ImageIcon coreImg = new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\snake\\img\\1.jpg");
    ImageIcon biteImg = new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\snake\\img\\2.jpg");
    ImageIcon appleImg = new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\snake\\img\\3.jpg");
    ImageIcon background = new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\snake\\img\\snakeBG.jpg");
    Image img1;
    Image img2;
    Image img3;
    ImageIcon headE = new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\snake\\headImg\\headE.jpg");
    ImageIcon headW = new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\snake\\headImg\\headW.jpg");
    ImageIcon headN = new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\snake\\headImg\\headN.jpg");
    ImageIcon headS = new ImageIcon("E:\\Coding\\NLU\\HK2Y1\\Matcha2023\\JSwing2023\\src\\snake\\headImg\\headS.jpg");
    Image snakeE, snakeW, snakeN, snakeS;
    public ImageOperation(int UNIT_SIZE) {
        img1 = coreImg.getImage().getScaledInstance(UNIT_SIZE, UNIT_SIZE, Image.SCALE_SMOOTH);
        img2 = biteImg.getImage().getScaledInstance(UNIT_SIZE, UNIT_SIZE, Image.SCALE_SMOOTH);
        img3 = appleImg.getImage().getScaledInstance(UNIT_SIZE, UNIT_SIZE, Image.SCALE_SMOOTH);
        Image bg = background.getImage().getScaledInstance(600 + 500, 600, Image.SCALE_SMOOTH);
        background = new ImageIcon(bg);
        coreImg = new ImageIcon(img1);
        biteImg = new ImageIcon(img2);
        appleImg = new ImageIcon(img3);

        snakeE = headE.getImage().getScaledInstance(UNIT_SIZE, UNIT_SIZE, Image.SCALE_SMOOTH);
        snakeW = headW.getImage().getScaledInstance(UNIT_SIZE, UNIT_SIZE, Image.SCALE_SMOOTH);
        snakeS = headS.getImage().getScaledInstance(UNIT_SIZE, UNIT_SIZE, Image.SCALE_SMOOTH);
        snakeN = headN.getImage().getScaledInstance(UNIT_SIZE, UNIT_SIZE, Image.SCALE_SMOOTH);

        headE = new ImageIcon(snakeE);
        headW = new ImageIcon(snakeW);
        headS = new ImageIcon(snakeS);
        headN = new ImageIcon(snakeN);

    }

    public ImageIcon getBackground() {
        return background;
    }
    public Image getImg(int ID) {
        switch (ID) {
            case 1: {
                return coreImg.getImage();
            }
            case 2: {
                return biteImg.getImage();
            }
            case 3: {
                return appleImg.getImage();
            }
        }
        return null;
    }
    public Image getSnakeHead(char direction) {
        switch (direction) {
            case 'E' -> {
                return headE.getImage();
            }
            case 'W' -> {
                return headW.getImage();
            }
            case 'N' -> {
                return headN.getImage();
            }
            case 'S' -> {
                return headS.getImage();
            }
        }
        return null;
    }
}
