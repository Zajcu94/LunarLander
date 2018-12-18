package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 *
 * Glowna ramka programu
 *
 */
public class MainFrame extends JFrame {
    private Future<?> gameTask;
    int pierwszy_raz=1;
    private ExecutorService gameExecutor = Executors.newSingleThreadExecutor();

    /**
     * Zmienna przechowujaca tytul gry
     */
    private final static String TITLE = "LunarLander";
    /**
     * Zmienna przechowujaca standardowa szerokosc okna gry
     */
    private final static int DEFAULT_WIDTH = 520;
    /**
     * Zmienna przechowujaca standardowa wysokosc okna gry
     */
    private final static int DEFAULT_HIGHT = 570;
    /**
     * Zmienna przechowujaca standardowa wysokosc okna gry
     */
    private String mapNames = "listaMap.txt";
    /**
     * Parametr odpowiedzialny za wybrany poziom trudnosci hard - true
     */
    private boolean status = false;
    /**
     * Zmienna przechowujaca nazwe gracza
     */
    private String nickname;

    /**
     * Bezparametrowy konstruktor tworzacy glowna ramke aplikacji
     */
    public MainFrame() {
        setTitle(TITLE);
        setVisible(true);
        setBounds(new Rectangle(DEFAULT_WIDTH, DEFAULT_HIGHT));
        makeMenu();
        this.setMinimumSize(new Dimension(400, 400));
        this.setResizable(true);
        // startNewGame(); // this does work
        revalidate();
    }

    /**
     * uruchomienie nowej gry
     */
    public void startNewGame() {


        if(pierwszy_raz!=1)
        {
            this.dispose();
            JFrame frame = new view.MainFrame();
        }
        if(pierwszy_raz==1)
            pierwszy_raz++;
        if (gameTask != null)
            gameTask.cancel(true);
        gameTask = gameExecutor.submit(new MyRunnable(this, DEFAULT_WIDTH, DEFAULT_HIGHT, mapNames) {
        });
    }


    /**
     * Metoda tworzaca menu głowne gry
     */
    private void makeMenu() {
        JMenuBar menuBar = new JMenuBar();

        JMenu menuGame = new JMenu("Gra");

        menuGame.add(new AbstractAction("Nowa gra") {
            public void actionPerformed(ActionEvent event) {
                JFrame nickInfo = new JFrame();
                String tmpNick = "";
                tmpNick = JOptionPane.showInputDialog("Podaj swoj nick");
                setNickname(tmpNick);
                startNewGame();
                // startGame();
            }
        });

        menuGame.add(new AbstractAction("Najlepsze wyniki") {
            public void actionPerformed(ActionEvent event) {
                String fileName = "info\\highScore.txt";
                StringBuilder instrukcjaGry = new StringBuilder();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(fileName));
                    String tmp = null;
                    while ((tmp = reader.readLine()) != null) {

                        instrukcjaGry.append(tmp);
                        instrukcjaGry.append("\n");

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                JFrame frameInfo = new JFrame();
                JOptionPane.showMessageDialog(frameInfo, instrukcjaGry, "LunarLander - Top10", 1);
            }
        });

        menuGame.add(new AbstractAction("Wyjscie") {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

        JMenu menuSettings = new JMenu("Ustawienia");
        ButtonGroup levelSettings = new ButtonGroup();
        JRadioButtonMenuItem easyLevel = new JRadioButtonMenuItem("Poziom latwy");
        easyLevel.setSelected(true);
        JRadioButtonMenuItem hardLevel = new JRadioButtonMenuItem("Poziom trudny");
        levelSettings.add(easyLevel);
        levelSettings.add(hardLevel);
        hardLevel.addActionListener(evt -> {
            status = true;

        });
        easyLevel.addActionListener(e -> {
            status = false;
        });



        menuSettings.add(easyLevel);
        menuSettings.add(hardLevel);
        menuSettings.addSeparator();


        JMenu menuHelp = new JMenu("Pomoc");
        menuHelp.add(new AbstractAction("Instrukcja") {
            public void actionPerformed(ActionEvent event) {
                String fileName = "info\\info.txt";
                StringBuilder instrukcjaGry = new StringBuilder();
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new FileReader(fileName));
                    String tmp = null;
                    while ((tmp = reader.readLine()) != null) {

                        instrukcjaGry.append(tmp);
                        instrukcjaGry.append("\n");

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                JFrame frameInfo = new JFrame();
                JOptionPane.showMessageDialog(frameInfo, instrukcjaGry, "LunarLander - Info", 1);
            }
        });

        menuBar.add(menuGame);
        menuBar.add(menuSettings);
        menuBar.add(menuHelp);

        this.setJMenuBar(menuBar);

    }

    /**
     * Metoda zwracajaca nazwe gracza
     *
     * @return String zawierajacy nazwe gracza
     */
    public String getNickname() {
        return nickname;
    }


    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     *
     * @return status gry
     */
    public boolean isStatus() {
        return status;
    }
}