package view;

import model.LevelLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class ImagePanel extends JPanel implements ComponentListener, WindowStateListener {
    /**
     * Zmienna przechowujaca nasze tlo(obraz)
     */
    private Image img;
    /**
     * Zmienna sklaujac teksture obrazka w poziomie
     */
    private int spaceX;
    /**
     * Zmienna sklaujac teksture obrazka w pionie
     */
    private int spaceY;
    /**
     * Jest to JPanel na ktorym bedziemy wywolywac malowanie, lisnery
     */
    private MainFrame frame;
    /**
     * imPanel-
     *            tlo
     */
    private ImagePanel imPanel;


    /**
     * konstrukor klasy
     *
     * @param img zrodlo obrazka
     * @param frame  ramka
     * @param DX wspolrzedna pozioma
     * @param DY wspolrzedna pionowa
     * @param tmp tymczasowy string

     */
    public ImagePanel(Image img, MainFrame frame, int DX, int DY, String tmp) {

        LevelLoader level;
        level = new LevelLoader(tmp);

        spaceX = DX;
        spaceY = DY;
        this.frame = frame;
        this.img = img;
        setLayout(null);
        addComponentListener(this);
        frame.addWindowStateListener(this);

        this.setPreferredSize(new Dimension(DX, DY));
        frame.getContentPane().add(this, BorderLayout.NORTH);
        imPanel = this;
    }
    /**
     * metoda rysujaca komponent
     */
    public void paintComponent(Graphics g) {

        g.drawImage(img, 0, 0, spaceX, spaceY, this);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 30));
        g.setColor(Color.GREEN);
    }
    /**
     *
     * Event skalujacy panel wyswietlajacy informacje
     */
    public void componentResized(ComponentEvent e) {
        Rectangle r = getBounds();
        spaceX = r.width;
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentShown(ComponentEvent e) {
    }

    /**
     * Przeciazenie metody z WindowStateListner Wylicza nowe przeliczniki
     * tekstur podczas maxymalizowania i minimalizowania okna
     */
    public void windowStateChanged(WindowEvent evt) {
        int oldState = evt.getOldState();
        int newState = evt.getNewState();

        if ((oldState & Frame.MAXIMIZED_BOTH) == 0 && (newState & Frame.MAXIMIZED_BOTH) != 0) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            spaceX = (int) screenSize.getWidth();

        } else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0) {
            Rectangle r = frame.getBounds();
            spaceX = r.width;

        }

    }

//
}


