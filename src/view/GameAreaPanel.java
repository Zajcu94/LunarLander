package view;



import model.*;
import controller.*;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

/**
 * klasa zawierajaca panel w ktorym toczyc sie bedzie rozgrywka
 *
 */

public class GameAreaPanel extends JPanel implements ComponentListener, WindowStateListener, KeyListener {
    /**
     * lista przechowujaca elemnty do wyswietlenia na mapie;
     * map - cala mapa;
     * lands - miejsca w ktorzych nalezy wyladowac;
     * grounds podloze;
     * spaces- lista z elementami przestrzeni
     *
     */

    private ArrayList<AbstractElement> map = new ArrayList();
    private ArrayList<LandElement> lands = new ArrayList<>();
    private ArrayList<GroundElement> grounds = new ArrayList<>();

    private ArrayList<SpaceElement> spaces = new ArrayList<>();

    /**
     * zmienne przechowujace ilosc kolumn mapy
     */
    private int mapWidth;
    /**
     * Zmienna przechowujac ilosc wierszy mapy
     */
    private int mapHeight;
    /**
     * Zmienna skalujaca tekstury w poziomoe
     */
    private double spaceX;
    /**
     * Zmienna skalujaca tekstury w pionie
     */
    private double spaceY;
    /**
     * parametr przechowujacy chec zmiany polozenie gracz - w poziomie
     */
    private int dx;
    /**
     * parametr przechowujacy chec zmiany polozenie gracz - w pionie
     */
    private int dy;
    /**
     * przekazane okno gry, sluzy do ustalenia rozmiaru aktualnego okna +
     * oblsuga Eventu maxymalizowani i minimalizowaniu okna
     */
    private MainFrame frame;
    /**
     * Zmienna przechowujaca informacje o graczu
     */
    private PlayerElement player;
    /**
     * Flaga potrzebna do animacji ruchu
     */
    private boolean canMove = true;
    /**
     * przesuniecie w poziomie
     */
    private int moveX = 0;
    /**
     * przesuniecie w pionie
     */
    private int moveY = 0;


    /**
     * Konstruktor
     *
     * @param fileName sciezka dostepu do pliku z poziomem gry
     * @param frame przekazuje okna aby mozna bylo obsluzyc event oraz do obliczen zbalansowania tekstur
     * @param width szerokosc
     * @param height wysokosc
     */
    public GameAreaPanel(String fileName, MainFrame frame, int width, int height) {
        this.frame = frame;
        LevelLoader level;
        level = new LevelLoader(fileName);
        spaces.addAll(level.getSpaces());

        map.addAll(level.getMap());
        lands.addAll(level.getLands());
        grounds.addAll(level.getGrounds());
        player = level.getPlayer();
        mapHeight = level.getMapHeight();
        mapWidth = level.getMapWidth();
        spaceX = width / mapWidth;
        spaceY = (height - 120) / mapHeight;
        this.setLayout(null);
        this.addKeyListener(this);
        addComponentListener(this);
        frame.addWindowStateListener(this);
        frame.addKeyListener(this);
        frame.add(this);

    }


    /**
     * metoda rysujaca poziom
     */
    public void paint(Graphics g) {
        for (int i = 0; i < map.size(); i++) {
            AbstractElement item = (AbstractElement) map.get(i);
            g.drawImage(item.getImage(), item.getX() * (int) spaceX, item.getY() * (int) spaceY, (int) spaceX,
                    (int) spaceY, this);
        }

        g.drawImage(player.getImage(), player.getX() * (int) spaceX + moveX, player.getY() * (int) spaceY + moveY,
                (int) spaceX, (int) spaceY, this);

    }

    public void componentResized(ComponentEvent e) {
        Rectangle r = getBounds();
        spaceY = (r.height) / mapHeight;
        spaceX = r.width / mapWidth;
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
            spaceX = (screenSize.getWidth() - 60) / mapWidth;
            spaceY = (screenSize.getHeight() - 50) / mapHeight;
        } else if ((oldState & Frame.MAXIMIZED_BOTH) != 0 && (newState & Frame.MAXIMIZED_BOTH) == 0) {
            Rectangle r = frame.getBounds();
            spaceX = (r.width - 20) / mapWidth;
            spaceY = (r.height - 50) / mapHeight;
        }

    }

    public void keyPressed(KeyEvent e) {
        if (!canMove)
            return;
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = -1;
            move();
            return;
        }
        if (key == KeyEvent.VK_SPACE) {
            makePause();
            return;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 1;
            move();
            return;
        }

        if (key == KeyEvent.VK_UP) {
            dy = -1;
            move();
            return;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 1;
            move();
            return;
        }

    }
    /**
     * metoda zatrzymujaca gre
     */
    private void makePause() {


    }
    /**
     * Metoda odpowiedziala za ruch po planszy Jest wywolywana przez z
     * keyPressed
     */
    public void move() {
        if (checkGround(player.getX() + dx, player.getY() + dy))
            return;
        animacjaGracza(dx, dy);

    }

    /**
     * Metoda odpowiedzialna za animacje gracza
     * @param dx przesuniecie w poziomie
     * @param dy przesuniecie w pionie
     */
    public void animacjaGracza(int dx, int dy) {
        canMove = false;
        System.out.println("Pl: " + player.getX() + "  " + player.getY());
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!canMove) {
                        for (int i = 1; i < 50; i++) {
                            moveX = (int) spaceX * dx * i / 50;
                            moveY = (int) spaceY * dy * i / 50;
                            frame.repaint();
                            Thread.sleep(5);
                        }
                        moveX = 0;
                        moveY = 0;
                        canMove = true;
                        player.setXY(player.getX() + dx, player.getY() + dy);
                    }
                } catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        });
        t.start();
    }

    /**
     * @param dx
     *            chec poruszenia sie na osi ox
     * @param dy
     *            chec poruszenia sie na osi oy
     * @return zwraca czy na drodze nie ma podloza
     */
    public boolean checkGround(int dx, int dy)
    {
        grounds.size();
        GroundElement tmp = new GroundElement(dx, dy);
        for (int i = 0; i < grounds.size(); i++)
        {
            if (tmp.equals(grounds.get(i)))
                return true;
        }
        return false;
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            dy = 0;
        }
    }

    public void keyTyped(KeyEvent e) {

    }


}





