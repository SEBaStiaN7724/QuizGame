package QuizGame;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Millionaire extends GameScreen {

    private int[] liczniki; 

    public Millionaire(ArrayList<String> imiona, String tryb, String kategoria, int iloscPytan) {
        super(imiona, tryb, kategoria, iloscPytan); 
                
        liczniki = new int[gracze.size()];

        for (Player p : gracze) {
            p.setPunkty(1000); // Startowa kwota
        }
        
        aktualizujStatus();
        pokazPytanie(); 
    }

    @Override
    protected void aktualizujStatus() {
        if (liczniki == null) return; // Zabezpieczenie przed błędem NullPointer

        Player g = gracze.get(indeksAktualnegoGracza);
        

        int zostalo = limitNaGracza - liczniki[indeksAktualnegoGracza];
        
        labelStatus.setText("Tura: " + g.getImie() + " | Portfel: " + g.getPunkty() + " zł | Do końca: " + zostalo);
    }

    @Override
    protected void pokazPytanie() {
        super.pokazPytanie(); // Wywołujemy logikę bazową (pobranie pytania, zegar)

        if (liczniki == null || numerPytania >= listaPytan.size()) return;

        Question q = listaPytan.get(numerPytania);
        int nrPytaniaGracza = liczniki[indeksAktualnegoGracza] + 1;

        labelPytanie.setText("<html><center>Pytanie " + nrPytaniaGracza + " z " + limitNaGracza + 
                             "<br>[" + q.kategoria + "]<br>" + q.tresc + "</center></html>");
    }

    @Override
    public void sprawdzOdpowiedz(int wybranyIndeks) {
        zegar.stop();
        Question q = listaPytan.get(numerPytania);
        Player aktualny = gracze.get(indeksAktualnegoGracza);

        liczniki[indeksAktualnegoGracza]++;

        if (wybranyIndeks == q.poprawnyIndeks) {
            aktualny.dodajPunkty(500);
            JOptionPane.showMessageDialog(this, "Dobrze! +500 zł");
        } else {
            aktualny.dodajPunkty(-1000); 
            String info = (wybranyIndeks == -1) ? "Czas minął!" : "Błąd!";
            JOptionPane.showMessageDialog(this, info + " -1000 zł\nPoprawna: " + q.odpowiedzi[q.poprawnyIndeks]);
        }

        // Sprawdzamy warunki końca dla gracza
        if (aktualny.getPunkty() < 0) {
            JOptionPane.showMessageDialog(this, "Bankructwo! Odpadasz.");
        } else if (liczniki[indeksAktualnegoGracza] >= limitNaGracza) { 
            JOptionPane.showMessageDialog(this, "Koniec Twoich pytań. Zabierasz: " + aktualny.getPunkty() + " zł");
        }

        przejdzDoNastepnego();
    }

    private void przejdzDoNastepnego() {
        for (int i = 0; i < gracze.size(); i++) {
            indeksAktualnegoGracza++; 
            if (indeksAktualnegoGracza >= gracze.size()) {
                indeksAktualnegoGracza = 0;
            }

            if (gracze.get(indeksAktualnegoGracza).getPunkty() >= 0 && liczniki[indeksAktualnegoGracza] < limitNaGracza) {
                numerPytania++;
                if (numerPytania < listaPytan.size()) {
                    pokazPytanie();
                } else {
                    koniecGry();
                }
                return;
            }
        }
        koniecGry();
    }
}