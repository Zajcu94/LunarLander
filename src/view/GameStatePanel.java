package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * klasa zawierajaca Panel z informacjaim o rozgrywce
 */
public class GameStatePanel extends JPanel {
    /**
     * Zmiena przechowujaca standardowa wysokosc panelu
     */
    private final static int DEFAULT_SPACEY = 60;
    /**
     * Zmienna przechowujaca standardowa szerokosc panelu
     */
    private final static int DEFAULT_SPACEX = 520;
    /**
     * Tlo naszego panelu wynikow
     */
    private ImagePanel panel;

    /**
     * konstruktor klasy
     *
     * @param frame
     *            ramka glowna
     * @param tmp
     * 			tyczasowy pomocniczy string

     */
    public GameStatePanel(MainFrame frame, String tmp) {
        panel = new ImagePanel(new ImageIcon("pic\\background.jpg").getImage(), frame, DEFAULT_SPACEX, DEFAULT_SPACEY,
                tmp);

    }
    /**
     * @return Zwraca nasze tlo
     */
    public ImagePanel getPanel() {
        return panel;
    }

}