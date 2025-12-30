package QuizGame; 

import javax.swing.Timer;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;

public class Clock {
    public int czas;
    public JLabel etykieta;
    public Timer timer;
    public GameScreen gra; 


    public Clock(JLabel etykieta, GameScreen gra) {
        this.etykieta = etykieta;
        this.gra = gra;

        // Tworzymy timer, który wywołuje tikTak() co sekundę
        this.timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tikTak();
            }
        });
    }

    public void start(int ileSekund) {
        this.czas = ileSekund;
        this.timer.start();
        aktualizujWyglad();
    }

    public void stop() {
        this.timer.stop();
    }

    public void tikTak() {
        czas--;
        aktualizujWyglad();

        if (czas <= 0) {
            timer.stop();
            gra.czasSieSkonczyl();
        }
    }

    public void aktualizujWyglad() {
        etykieta.setText("Czas: " + czas + "s");
        
        // Zmiana koloru na czerwony, gdy zostaje mniej niż 5 sekund
        if (czas <= 5) {
            etykieta.setForeground(Color.RED);
        } else {
            etykieta.setForeground(Color.WHITE);
        }
    }
}