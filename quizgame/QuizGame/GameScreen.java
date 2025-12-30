package QuizGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameScreen extends JFrame {

    protected ArrayList<Question> listaPytan;
    protected int numerPytania = 0;
    
    protected ArrayList<Player> gracze;
    protected int indeksAktualnegoGracza = 0;

    protected String wybranaKategoria; 
    protected String trybGry; 
    
    protected JLabel labelPytanie;
    protected JLabel labelStatus;
    protected JLabel labelCzas;
    protected JButton[] przyciski;
    
    protected Clock zegar;

    public GameScreen(ArrayList<String> imiona, String tryb, String kategoria, int iloscPytanNaGracza) {
        this.gracze = new ArrayList<>();
        for (String imie : imiona) {
            this.gracze.add(new Player(imie));
        }

        this.trybGry = tryb; 
        this.wybranaKategoria = kategoria;

        setTitle("Quiz - " + tryb + " [" + kategoria + "]");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Colors.LIGHT_BLUE);

        // Wczytujemy pytania
        this.listaPytan = QuestionLoader.wczytajPytania("QuizGame/pytania.txt", kategoria);

        // Obliczamy ile łącznie potrzeba
        int wymaganaLiczbaPytan = iloscPytanNaGracza * gracze.size();
    
        if (listaPytan.size() > wymaganaLiczbaPytan) {
            this.listaPytan = new ArrayList<>(listaPytan.subList(0, wymaganaLiczbaPytan));
        }

        if (listaPytan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Błąd: Brak pytań.");
            dispose();
            new QuizConfigScreen();
            return;
        }

        budujInterfejs();
        zegar = new Clock(labelCzas, this);
        pokazPytanie();
        setVisible(true);
    }

    private void budujInterfejs() {
        JPanel panelGora = new JPanel(new GridLayout(2, 1));
        panelGora.setBackground(Colors.DARK_BLUE);

        labelStatus = new JLabel("", SwingConstants.CENTER);
        aktualizujStatus();
        labelStatus.setForeground(Color.WHITE);
        labelStatus.setFont(new Font("Arial", Font.BOLD, 18));
        
        labelCzas = new JLabel("Czas: --", SwingConstants.CENTER);
        labelCzas.setForeground(Color.WHITE);
        labelCzas.setFont(new Font("Arial", Font.BOLD, 20));
        
        panelGora.add(labelStatus);
        panelGora.add(labelCzas);
        add(panelGora, BorderLayout.NORTH);

        JPanel panelSrodek = new JPanel(new GridLayout(5, 1, 10, 10));
        panelSrodek.setBackground(Colors.LIGHT_BLUE);

        labelPytanie = new JLabel("Pytanie...", SwingConstants.CENTER);
        labelPytanie.setFont(new Font("Arial", Font.BOLD, 20));
        panelSrodek.add(labelPytanie);

        przyciski = new JButton[4];
        for (int i = 0; i < 4; i++) {
            przyciski[i] = new JButton("");
            przyciski[i].setBackground(Colors.BRIGHT_YELLOW);
            przyciski[i].setFont(new Font("Arial", Font.PLAIN, 16));
            final int idx = i;
            przyciski[i].addActionListener(e -> sprawdzOdpowiedz(idx));
            panelSrodek.add(przyciski[i]);
        }
        add(panelSrodek, BorderLayout.CENTER);
    }

    protected void pokazPytanie() {
        if (numerPytania >= listaPytan.size()) {
            koniecGry();
            return;
        }
        
        aktualizujStatus();

        Question q = listaPytan.get(numerPytania);
        // Wyświetlamy też numer pytania w nagłówku
        labelPytanie.setText("<html><center>Pytanie " + (numerPytania + 1) + " z " + listaPytan.size() + "<br>[" + q.kategoria + "]<br>" + q.tresc + "</center></html>");
        
        for (int i = 0; i < 4; i++) {
            przyciski[i].setText(q.odpowiedzi[i]);
        }
        zegar.start(30);
    }

    public void czasSieSkonczyl() {
        JOptionPane.showMessageDialog(this, "Koniec czasu!");
        sprawdzOdpowiedz(-1);
    }

    public void sprawdzOdpowiedz(int wybranyIndeks) {
        zegar.stop();
        Question q = listaPytan.get(numerPytania);
        Player aktualnyGracz = gracze.get(indeksAktualnegoGracza);
        
        if (wybranyIndeks == -1) {
            // Czas minął
        } else if (wybranyIndeks == q.poprawnyIndeks) {
            aktualnyGracz.punkty++; 
            JOptionPane.showMessageDialog(this, "Dobrze! Punkt dla: " + aktualnyGracz.imie);
        } else {
            JOptionPane.showMessageDialog(this, "Źle! Poprawna to: " + q.odpowiedzi[q.poprawnyIndeks]);
        }
        
        indeksAktualnegoGracza++;
        if (indeksAktualnegoGracza >= gracze.size()) {
            indeksAktualnegoGracza = 0;
        }

        numerPytania++;
        pokazPytanie();
    }
    
    protected void aktualizujStatus() {
        Player g = gracze.get(indeksAktualnegoGracza);
        labelStatus.setText("Tura: " + g.imie + " | Pkt: " + g.punkty + " | " + trybGry);
    }

    public void koniecGry() {
        zegar.stop();
        StringBuilder wyniki = new StringBuilder("Koniec gry!\n");
        for (Player p : gracze) {
            ScoreManager.zapiszWynik(p.imie, p.punkty, trybGry);
            wyniki.append(p.imie).append(": ").append(p.punkty).append(" pkt\n");
        }
        
        String top = ScoreManager.pobierzNajlepszeWyniki(trybGry);
        JOptionPane.showMessageDialog(this, wyniki.toString() + "\n" + top);
        
        dispose();
        new QuizConfigScreen();
    }
}