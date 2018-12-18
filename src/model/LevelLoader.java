package model;

import java.util.ArrayList;
/**
 * Klasa odpowiedzialna za stworzenie obiektow mapy
 *
 * DEFAULT_PLAYER gracz w pliku mapy
 * DEFAULT_GROUND podłoze w pliku mapy
 * DEFAULT_LAND miejsce do wyladowania w pliku mapy
 * DEFAULT_SPACE przestrzeń kosmiczna w pliku mapy
 * map lista obiektow poziomu
 * mapWidth szerokosc mapy
 * mapHeight wysokosc mapy
 */

public class LevelLoader {

    /**
     * '@' - zmienna opisujaca polozenie gracza w pliku txt
     */
    private final static char DEFAULT_PLAYER = '@';
    /**
     * # - zmienna opisujaca polozenie podloza w pliku txt
     */
    private final static char DEFAULT_GROUND = '#';
    /**
     *  o - zmienna opisujaca polozenie ladowiska w pliku txt
     */
    private final static char DEFAULT_LAND = 'o';
    /**
     * _ - zmienna opisujaca polozenie przestrzeni w pliku txt
     */
    private final static char DEFAULT_SPACE = '_';

    /**
     * Lista przechowujaca wszystkie elemnty na mapie
     */
    private ArrayList<AbstractElement> map = new ArrayList<>();
    /**
     * Lista przechowujaca ladowiska mapy
     */
    private ArrayList<LandElement> lands = new ArrayList<>();

    /**
     * Lista przechowujaca podloza mapy
     */
    private ArrayList<GroundElement> grounds = new ArrayList<>();
    /**
     * Zmienna przechowujaca ilosc kolun mapy
     */
    private int mapWidth;
    /**
     * Zmienna przechowujac ilosc wierszy mapy
     */
     private int mapHeight;
    /**
     * zmienne przechowujace polozenie gracza
     */
    private PlayerElement player;
    /**
     * Lista przechowujaca przestrzen kosmisczna mapy
     */
    private ArrayList<SpaceElement> spaces = new ArrayList<>();

    /**
     * @param fileName sciezka pliku z mapa
     */

    public LevelLoader(String fileName) {

        /**
         * Odczytanie pliku zawierajacego ustawienia mapy
         */
        DataFileReader file = new DataFileReader(fileName);
        mapHeight = file.getDataList().size() - 1;;
        char item;
        for (int i = 1; i < file.getDataList().size(); ++i) {

            /**
             * ustawienie wysokosci mapy na podstawie ilosc odczytanych wierszy
             */
            for (int j = 0; j < file.getDataList().get(i).length(); ++j) {

                /**
                 * Ustawienie szerokosc mapy na podstawie ilosc znakow w wierszu
                 */
                if(mapWidth < j+1) {mapWidth = j+1;}

                /**
                 * Odczytanie kolejnych znakow w wierszu
                 */
                item = file.getDataList().get(i).charAt(j);

                /**
                 * Jezeli odczytany znak to @  zostanie dodanie gracza do listy
                 */
                if (item == DEFAULT_PLAYER) // player
                {
                    spaces.add(new SpaceElement(j,i-1));
                    player = new PlayerElement(j, i - 1);
                    map.add(new SpaceElement(j, i - 1));

                }
                /**
                 * Jezeli odczytany znak to # zostanie dodane podloze do listy
                 */

                else if (item == DEFAULT_GROUND)// ground
                {
                    grounds.add(new GroundElement(j, i - 1));
                    map.add(new GroundElement(j, i - 1));
                }

                /**
                 * Jezeli odczytany znak to o  zostanie dodanie ladowisko do listy
                 */
                else if (item == DEFAULT_LAND) // land-spot
                {
                    spaces.add(new SpaceElement(j,i-1));
                    map.add(new LandElement(j, i - 1));
                    lands.add(new LandElement(j,i-1));
                }
                /**
                 * Jezeli odczytany znak to _  zostanie dodanie przestrzen do listy
                 */
                else if (item == DEFAULT_SPACE) // space
                {
                    spaces.add(new SpaceElement(j,i-1));
                    map.add(new SpaceElement(j, i - 1));
                }
            }
        }

    }

    /**
     * @return ArrayList zawierajacy obiekty z ktorych zbudowana jest mapa
     */
    public ArrayList<AbstractElement> getMap() {
        return map;
    }

    /**
     * @return szerokosc mapy
     */
    public int getMapWidth() {
        return mapWidth;
    }

    /**
     * @return wysokosc mapy
     */
    public int getMapHeight() {
        return mapHeight;
    }

    /**
     *
     * @return Zwraca polozenia gracza na mapie
     */

    public PlayerElement getPlayer() { return player;   }



    /**
     *
     * @return Zwraca liste ladowisk mapy
     */
    public ArrayList<LandElement> getLands () {return  lands;}

    /**
     *
     * @return Zwraca liste podloza na mapie
     */
    public ArrayList<GroundElement> getGrounds () {return grounds;}

    /**
     *
     * @return Zwraca liste przestrzeni na mapie
     */
    public ArrayList<SpaceElement> getSpaces() {
        return spaces;
    }
}