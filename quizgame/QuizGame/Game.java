package QuizGame;

import javax.swing.*;
import java.util.ArrayList;
/** Klasa Game zarządza logiką gry, dziedziczy po Screen (UI) */
// Game dziedziczy teraz po Screen (UI), a samo zajmuje się logiką
public class Game extends Screen {

    protected ArrayList<Question> listaPytan;
    protected int numerPytania = 0;
    
    protected ArrayList<Player> gracze;
    protected int indeksAktualnegoGracza = 0;

    protected String wybranaKategoria; 
    protected String trybGry; 
    protected int limitNaGracza;

    public Game(ArrayList<String> imiona, String tryb, String kategoria, int iloscPytanNaGracza) {
        // Wywołujemy konstruktor Screen, żeby stworzyć okno
        super("Quiz - " + tryb + " [" + kategoria + "]");
        
        this.trybGry = tryb;
        this.wybranaKategoria = kategoria;
        this.limitNaGracza = iloscPytanNaGracza;

        inicjalizujGraczy(imiona);
        inicjalizujPytania(kategoria, iloscPytanNaGracza);

        pokazPytanie();
    }

    private void inicjalizujGraczy(ArrayList<String> imiona) {
        this.gracze = new ArrayList<>();
        for (String imie : imiona) {
            this.gracze.add(new Player(imie));
        }
    }

    private void inicjalizujPytania(String kategoria, int iloscPytanNaGracza) {
        this.listaPytan = QuestionLoader.wczytajPytania("pytania.txt", kategoria);
        int wymaganaLiczba = iloscPytanNaGracza * gracze.size();
    
        if (listaPytan.size() > wymaganaLiczba) {
            this.listaPytan = new ArrayList<>(listaPytan.subList(0, wymaganaLiczba));
        }

        if (listaPytan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Błąd: Brak pytań.");
            dispose();
            new QuizConfigScreen();
        }
    }

    // --- LOGIKA GRY ---

    protected void pokazPytanie() {
        if (numerPytania >= listaPytan.size()) {
            koniecGry();
            return;
        }
        
        aktualizujStatus();

        Question q = listaPytan.get(numerPytania);
        int nrPytaniaGracza = (numerPytania / gracze.size()) + 1;

        // Ustawiamy teksty w elementach z klasy Screen
        labelPytanie.setText("<html><center>Pytanie " + nrPytaniaGracza + " z " + limitNaGracza + 
                             "<br>[" + q.getKategoria() + "]<br>" + q.getTresc() + "</center></html>");
        
        for (int i = 0; i < 4; i++) {
            przyciski[i].setText(q.getOdpowiedzi()[i]);
        }
        zegar.start(30);
    }

    protected void aktualizujStatus() {
        Player g = gracze.get(indeksAktualnegoGracza);
        labelStatus.setText("Tura: " + g.getImie() + " | Pkt: " + g.getPunkty() + " | " + trybGry);
    }

    @Override
    public void czasSieSkonczyl() {
        JOptionPane.showMessageDialog(this, "Koniec czasu!");
        sprawdzOdpowiedz(-1);
    }

    @Override
    public void sprawdzOdpowiedz(int wybranyIndeks) {
        zegar.stop();
        Question q = listaPytan.get(numerPytania);
        Player aktualnyGracz = gracze.get(indeksAktualnegoGracza);
        
        if (wybranyIndeks != -1) {
            if (wybranyIndeks == q.getPoprawnyIndeks()) {
                aktualnyGracz.dodajPunkty(1);
                JOptionPane.showMessageDialog(this, "Dobrze! Punkt dla: " + aktualnyGracz.getImie());
            } else {
                JOptionPane.showMessageDialog(this, "Źle! Poprawna to: " + q.getOdpowiedzi()[q.getPoprawnyIndeks()]);
            }
        }
        
        nastepnaTura();
    }

    protected void nastepnaTura() {
        indeksAktualnegoGracza++;
        if (indeksAktualnegoGracza >= gracze.size()) {
            indeksAktualnegoGracza = 0;
        }
        numerPytania++;
        pokazPytanie();
    }
    
    public void koniecGry() {
        zegar.stop();
        StringBuilder wyniki = new StringBuilder("Koniec gry!\n");
        for (Player p : gracze) {
            ScoreManager.zapiszWynik(p.getImie(), p.getPunkty(), trybGry);
            wyniki.append(p.getImie()).append(": ").append(p.getPunkty()).append(" pkt\n");
        }
        
        String top = ScoreManager.pobierzNajlepszeWyniki(trybGry);
        JOptionPane.showMessageDialog(this, wyniki.toString() + "\n" + top);
        
        dispose();
        new QuizConfigScreen();
    }
}