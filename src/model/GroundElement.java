package model;

/**
 * Klasa przedstawiajaca model pojedycznego obiektu podłoża
 */
public class GroundElement extends AbstractElement {
    /**
     * Zmienna przechowujaca sciezke do grafiki skrzynki
     */

    private final static String SOURCE = "pic\\ground.png";
    /**
     * MARK
     *            zmienna odpowiedzialna za symbol reprezentujacy element w
     *            plikach opisujacych dany poziom
     */
    private final static String MARK = "#";

    /**
     * Konstrutor
     *
     * @param x
     *            wspolzedna wyswietlenia grafiki
     * @param y
     *            wspolzedna wyswietlenia grafiki
     */
    public GroundElement(int x, int y) {
        /**
         * konstruktor odwolujacy sie do nadklasy AbstractElement ustawia
         * wspolrzedne X i Y oraz przypisuje źródło klasie IMAGE
         */
        super(x, y, SOURCE);


    }
    /**
     * Przeciazenie metody equals
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof GroundElement))
            return false;
        GroundElement s = (GroundElement) obj;
        if (this.getX() == s.getX() && this.getY() == s.getY())
            return true;
        return false;
    }

}
