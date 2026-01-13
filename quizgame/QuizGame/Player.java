package QuizGame;

public class Player {
    // ZMIANA: Pola są teraz prywatne (wymóg na punkty)
    private String imie;
    private int punkty;

    public Player(String imie) {
        this.imie = imie;
        this.punkty = 0; 
    }

    // Gettery (do odczytu)
    public String getImie() {
        return imie;
    }

    public int getPunkty() {
        return punkty;
    }

    // Settery i metody modyfikujące (do zapisu)
    public void setPunkty(int punkty) {
        this.punkty = punkty;
    }

    public void dodajPunkty(int ile) {
        this.punkty += ile;
    }
}