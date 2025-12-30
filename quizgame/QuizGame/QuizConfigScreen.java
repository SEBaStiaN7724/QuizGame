package QuizGame;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class QuizConfigScreen extends JFrame {
    JSpinner spinnerPytania;
    JSpinner spinnerGracze;
    JComboBox<String> comboKategoria;
    JComboBox<String> comboTryb;
    JTextField poleImie;
    JButton btnStart;

    public QuizConfigScreen() {
        setTitle("Konfiguracja Gry Quizowej");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2, 10, 10));
        getContentPane().setBackground(Colors.LIGHT_BLUE);
        setLocationRelativeTo(null);

        add(new JLabel("  Imie (Gracz 1):", SwingConstants.LEFT));
        poleImie = new JTextField("Gracz 1");
        add(poleImie);

        add(new JLabel("  Liczba Pytań (na gracza):", SwingConstants.LEFT));
        spinnerPytania = new JSpinner(new SpinnerNumberModel(5, 1, 50, 1));
        add(spinnerPytania);

        add(new JLabel("  Liczba Graczy (max 4):", SwingConstants.LEFT));
        spinnerGracze = new JSpinner(new SpinnerNumberModel(1, 1, 4, 1));
        add(spinnerGracze);

        add(new JLabel("  Kategoria:", SwingConstants.LEFT));
        String[] kategorie = {"Ogólny", "Historia", "Nauka", "Sport", "Geografia"};
        comboKategoria = new JComboBox<>(kategorie);
        add(comboKategoria);

        add(new JLabel("  Tryb Gry:", SwingConstants.LEFT));
        String[] tryby = {"Klasyczny", "Milionerzy", "Survival"};
        comboTryb = new JComboBox<>(tryby);
        add(comboTryb);
        
        add(new JLabel("")); 
        btnStart = new JButton("Rozpocznij Grę");
        btnStart.setBackground(Colors.BRIGHT_YELLOW);
        btnStart.addActionListener(e -> pobierzUstawieniaIStartu());
        add(btnStart);

        setVisible(true);
    }

    void pobierzUstawieniaIStartu() {
        String imieGracza1 = poleImie.getText();
        String tryb = (String) comboTryb.getSelectedItem();
        String kategoria = (String) comboKategoria.getSelectedItem();
        int iloscPytan = (int) spinnerPytania.getValue();
        int liczbaGraczy = (int) spinnerGracze.getValue();

        if (tryb.equals("Survival") && liczbaGraczy > 1) {
            JOptionPane.showMessageDialog(this, "Tryb Survival jest tylko dla 1 gracza!");
            spinnerGracze.setValue(1); 
            return;
        }

        // Sprawdzamy ile pytań realnie potrzebujemy
        int potrzebnePytania = iloscPytan * liczbaGraczy;
        ArrayList<Question> dostepne = QuestionLoader.wczytajPytania("QuizGame/pytania.txt", kategoria);
        
        if (dostepne.size() < potrzebnePytania) {
            JOptionPane.showMessageDialog(this, 
                "Nie można rozpocząć gry!\n\n" +
                "Wybrano: " + liczbaGraczy + " graczy po " + iloscPytan + " pytań.\n" +
                "Potrzeba łącznie: " + potrzebnePytania + " pytań.\n" +
                "W bazie dla kategorii '" + kategoria + "' jest tylko: " + dostepne.size() + ".\n\n" +
                "Zmniejsz liczbę pytań lub wybierz inną kategorię.");
            return;
        }
   
        // Jeśli przeszliśmy walidację, zbieramy imiona
        ArrayList<String> imionaGraczy = new ArrayList<>();
        imionaGraczy.add(imieGracza1); 

        for (int i = 2; i <= liczbaGraczy; i++) {
            String n = JOptionPane.showInputDialog(this, "Podaj imię dla Gracza " + i + ":");
            if (n == null || n.trim().isEmpty()) {
                n = "Gracz " + i;
            }
            imionaGraczy.add(n);
        }

        dispose();

        if (tryb.equals("Survival")) {
            new Survival(imionaGraczy, tryb, kategoria, iloscPytan);
        } else if (tryb.equals("Milionerzy")) {
            new MillionaireGameScreen(imionaGraczy, tryb, kategoria, iloscPytan);
        } else {
            new GameScreen(imionaGraczy, tryb, kategoria, iloscPytan);
        }
    }
}