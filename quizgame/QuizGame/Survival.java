package QuizGame;
import javax.swing.JOptionPane;
import java.util.ArrayList;

public class Survival extends GameScreen {

    public Survival(ArrayList<String> imiona, String tryb, String kategoria, int iloscPytan) {
        super(imiona, tryb, kategoria, iloscPytan); 
        labelStatus.setText("SURVIVAL (" + kategoria + ") - Jeden błąd kończy grę!");
    }

    @Override
    public void sprawdzOdpowiedz(int wybranyIndeks) {
        zegar.stop();
        Question q = listaPytan.get(numerPytania);
        Player jedynyGracz = gracze.get(0); 

        if (wybranyIndeks == q.poprawnyIndeks) {
            jedynyGracz.dodajPunkty(1); 
            JOptionPane.showMessageDialog(this, "Dobrze! Przetrwałeś.");
            aktualizujStatus();
            numerPytania++;
            pokazPytanie();
        } else {
            String poprawna = (wybranyIndeks == -1) ? "Czas minął!" : "Błąd!";
            JOptionPane.showMessageDialog(this, poprawna + "\nPoprawna: " + q.odpowiedzi[q.poprawnyIndeks]);
            koniecGry();
        }
    }
    
    @Override
    public void koniecGry() {
        Player p = gracze.get(0);
        ScoreManager.zapiszWynik(p.getImie(), p.getPunkty(), "Survival");
        String top = ScoreManager.pobierzNajlepszeWyniki("Survival");
        JOptionPane.showMessageDialog(this, "Koniec Survivalu! Wynik: " + p.getPunkty() + "\n\n" + top);
        dispose();
        new QuizConfigScreen();
    }
}