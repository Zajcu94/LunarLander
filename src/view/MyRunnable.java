package view;


import javax.swing.*;
import java.io.*;
import java.util.ArrayList;

public class MyRunnable implements Runnable {
    /**
     * Zmienna przechowujaca panel gry
     */
    private GameAreaPanel tmpCurrentArea;
    /**
     * Zmienna przechowujaca panel wynikow
     */
    private GameStatePanel tmpCurrentState;
    /**
     * Zmienna potrzebna do odczytywania listy map
     */
    private BufferedReader tmpReader;

    /**
     * zmiennap przechowujaca ekran gry
     */
    private MainFrame frame;
    /**
     * Zmienna przechowujaca odczytany w danej chwili nazwe mapy
     */
    private String tmp = null;
    /**
     * Zmienne przechowujaca standardowe wymiary mapy
     *
     * DEFAULT_WIDTH szerokosc
     */
    private int DEFAULT_WIDTH;
    /**
     * * DEFAULT_HIGHT wysokosc
     *
     */
    private int DEFAULT_HIGHT;
    /**
     * Zmienna informujaca ktora to mapa
     */
    private String mapNames;

    /**
     * konstruktor klasy
     *
     * @param frame
     *            ramka z gra
     * @param DEFAULT_WIDTH
     *            szerokosc ramki
     * @param DEFAULT_HIGHT
     *            wysokosc ramki
     * @param mapNames
     *            lista z mapami gry
     */
    public MyRunnable(MainFrame frame, int DEFAULT_WIDTH, int DEFAULT_HIGHT, String mapNames) {
        this.mapNames = mapNames;
        this.frame = frame;
        this.DEFAULT_HIGHT = DEFAULT_HIGHT;
        this.DEFAULT_WIDTH = DEFAULT_WIDTH;
        try {
            tmpReader = new BufferedReader(new FileReader(mapNames));
            if ((tmp = tmpReader.readLine()) != null) {
                tmpCurrentArea = new GameAreaPanel(tmp, frame, DEFAULT_WIDTH, DEFAULT_HIGHT);
                tmpCurrentState = new GameStatePanel(frame, tmp);
                tmpCurrentState.getPanel().repaint();
                frame.revalidate();
            } else
                return;
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                tmpReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {

    }

}
