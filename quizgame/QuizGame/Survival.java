package QuizGame;
import javax.swing.JOptionPane;
import java.util.ArrayList;
/** Klasa Survival implementuje tryb "Survival" */
public class Survival extends Game {

    public Survival(ArrayList<String> imiona, String tryb, String kategoria, int iloscPytan) {
        super(imiona, tryb, kategoria, iloscPytan); 

    }
    @Override
    protected void aktualizujStatus() {
        if (gracze.isEmpty()) return;
        
        Player p = gracze.get(0); 
        // Wyświetlamy serię poprawnych odpowiedzi jako punkty
        labelStatus.setText("SURVIVAL (" + wybranaKategoria + ") | Seria: " + p.getPunkty() + " | Jeden błąd kończy grę!");
    }

    @Override
    public void sprawdzOdpowiedz(int wybranyIndeks) {
        zegar.stop();
        Question q = listaPytan.get(numerPytania);
        Player jedynyGracz = gracze.get(0); 

        if (wybranyIndeks == q.getPoprawnyIndeks()) {
            jedynyGracz.dodajPunkty(1); 
            JOptionPane.showMessageDialog(this, "Dobrze! Przetrwałeś.");
            numerPytania++;
            pokazPytanie();
        } else {
            String poprawna = (wybranyIndeks == -1) ? "Czas minął!" : "Błąd!";
            JOptionPane.showMessageDialog(this, poprawna + "\nPoprawna: " + q.getOdpowiedzi()[q.getPoprawnyIndeks()]);
            koniecGry();
        }
    }
    
    @Override
    public void koniecGry() {
        Player p = gracze.get(0);
        ScoreManager.zapiszWynik(p.getImie(), p.getPunkty(), "Survival");
        String top = ScoreManager.pobierzNajlepszeWyniki("Survival");
        
        // Wyświetlamy wynik jako "Przetrwano X pytań"
        JOptionPane.showMessageDialog(this, "Koniec Survivalu!\nPrzetrwano pytań: " + p.getPunkty() + "\n\n" + top);
        dispose();
        new QuizConfigScreen();
    }
}