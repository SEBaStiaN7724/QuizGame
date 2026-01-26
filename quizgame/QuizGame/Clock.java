package QuizGame; 
/** Klasa Clock zarządza odliczaniem czasu w grze quizowej */
import javax.swing.Timer;
import javax.swing.JLabel;
import java.awt.Color;

public class Clock {
    // ZMIANA: Pola są teraz prywatne (Hermetyzacja)
    private int czas;
    private JLabel etykieta;
    private Timer timer;
    private Screen ekran; 

    public Clock(JLabel etykieta, Screen ekran) {
        this.etykieta = etykieta;
        this.ekran = ekran;

        // Lambda jest bezpieczna, odwołuje się do metody wewnątrz klasy
        this.timer = new Timer(1000, e -> tikTak());
    }

    public void start(int ileSekund) {
        this.czas = ileSekund;
        this.timer.start();
        aktualizujWyglad();
    }

    public void stop() {
        this.timer.stop();
    }

    // Metoda może pozostać publiczna, jeśli timer wywołuje ją z zewnątrz pakietu,
    // ale logicznie jest używana wewnętrznie. Dla pewności zostawiamy public lub protected.
    private void tikTak() {
        czas--;
        aktualizujWyglad();

        if (czas <= 0) {
            timer.stop();
            ekran.czasSieSkonczyl();
        }
    }

    private void aktualizujWyglad() {
        etykieta.setText("Czas: " + czas + "s");
        if (czas <= 5) {
            etykieta.setForeground(Color.RED);
        } else {
            etykieta.setForeground(Color.WHITE);
        }
    }
    
    // Opcjonalnie: Getter, jeśli ktoś chciałby sprawdzić ile czasu zostało
    public int getCzas() {
        return czas;
    }
}