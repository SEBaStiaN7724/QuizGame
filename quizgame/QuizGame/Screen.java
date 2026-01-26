package QuizGame;
/** Abstrakcyjna klasa Screen definiuje wspólny interfejs GUI dla ekranów gry */
import javax.swing.*;
import java.awt.*;

public abstract class Screen extends JFrame {

    // Elementy GUI widoczne dla klas dziedziczących
    protected JLabel labelPytanie;
    protected JLabel labelStatus;
    protected JLabel labelCzas;
    protected JButton[] przyciski;
    protected Clock zegar;

    public Screen(String tytul) {
        setTitle(tytul);
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        
        budujInterfejs();
        
        // Inicjalizacja zegara (przekazujemy 'this' jako Screen)
        zegar = new Clock(labelCzas, this);
        
        setVisible(true);
    }

    private void budujInterfejs() {
        // Panel Górny
        JPanel panelGora = new JPanel(new GridLayout(2, 1));
        panelGora.setBackground(Colors.DARK_BLUE);

        labelStatus = new JLabel("Status...", SwingConstants.CENTER);
        labelStatus.setForeground(Color.WHITE);
        labelStatus.setFont(new Font("Arial", Font.BOLD, 18));
        
        labelCzas = new JLabel("Czas: --", SwingConstants.CENTER);
        labelCzas.setForeground(Color.WHITE);
        labelCzas.setFont(new Font("Arial", Font.BOLD, 20));
        
        panelGora.add(labelStatus);
        panelGora.add(labelCzas);
        add(panelGora, BorderLayout.NORTH);

        // Panel Środkowy (Pytanie i przyciski)
        JPanel panelSrodek = new JPanel(new GridLayout(5, 1, 10, 10));
        panelSrodek.setBackground(Colors.LIGHT_BLUE);

        labelPytanie = new JLabel("Wczytywanie...", SwingConstants.CENTER);
        labelPytanie.setFont(new Font("Arial", Font.BOLD, 20));
        panelSrodek.add(labelPytanie);

        przyciski = new JButton[4];
        for (int i = 0; i < 4; i++) {
            przyciski[i] = new JButton("");
            przyciski[i].setBackground(Colors.BRIGHT_YELLOW);
            przyciski[i].setFont(new Font("Arial", Font.PLAIN, 16));
            
            final int idx = i;
            // Podpinamy akcję do metody abstrakcyjnej
            przyciski[i].addActionListener(e -> sprawdzOdpowiedz(idx));
            
            panelSrodek.add(przyciski[i]);
        }
        add(panelSrodek, BorderLayout.CENTER);
    }

    // Metody abstrakcyjne - logika musi je zaimplementować
    public abstract void sprawdzOdpowiedz(int wybranyIndeks);
    public abstract void czasSieSkonczyl();
}