package QuizGame;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class MillionaireGameScreen extends GameScreen {

    private int limitPytan;
    private int[] liczniki; 

    public MillionaireGameScreen(ArrayList<String> imiona, String tryb, String kategoria, int iloscPytan) {
        super(imiona, tryb, kategoria, iloscPytan); 
        
        this.limitPytan = iloscPytan;

        for (Player p : gracze) {
            p.punkty = 1000;
        }
        
        aktualizujStatus();
    }

    @Override
    protected void aktualizujStatus() {

        if (liczniki == null) {
            liczniki = new int[gracze.size()];
        }

        Player g = gracze.get(indeksAktualnegoGracza);
        int zostalo = limitPytan - liczniki[indeksAktualnegoGracza];
        
        labelStatus.setText("Tura: " + g.imie + " | Portfel: " + g.punkty + " zł | Pytania: " + zostalo);
    }

    @Override
    public void sprawdzOdpowiedz(int wybranyIndeks) {
        zegar.stop();
        Question q = listaPytan.get(numerPytania);
        Player aktualny = gracze.get(indeksAktualnegoGracza);

        liczniki[indeksAktualnegoGracza]++;

        if (wybranyIndeks == q.poprawnyIndeks) {
            aktualny.punkty += 500;
            JOptionPane.showMessageDialog(this, "Dobrze! +500 zł");
        } else {
            aktualny.punkty -= 1000;
            String info = (wybranyIndeks == -1) ? "Czas minął!" : "Błąd!";
            JOptionPane.showMessageDialog(this, info + " -1000 zł\nPoprawna: " + q.odpowiedzi[q.poprawnyIndeks]);
        }

        if (aktualny.punkty < 0) {
            JOptionPane.showMessageDialog(this, "Bankructwo! Odpadasz.");
        } else if (liczniki[indeksAktualnegoGracza] >= limitPytan) {
            JOptionPane.showMessageDialog(this, "Koniec Twoich pytań. Zabierasz: " + aktualny.punkty + " zł");
        }

        przejdzDoNastepnego();
    }

    private void przejdzDoNastepnego() {
        for (int i = 0; i < gracze.size(); i++) {
            indeksAktualnegoGracza++; 
            if (indeksAktualnegoGracza >= gracze.size()) {
                indeksAktualnegoGracza = 0;
            }

            if (gracze.get(indeksAktualnegoGracza).punkty >= 0 && liczniki[indeksAktualnegoGracza] < limitPytan) {
                numerPytania++;
                if (numerPytania < listaPytan.size()) pokazPytanie();
                else koniecGry();
                return;
            }
        }
        koniecGry();
    }
}