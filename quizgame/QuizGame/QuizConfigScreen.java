package QuizGame;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class QuizConfigScreen extends JFrame {
    JSpinner spinnerPytania;
    JSpinner spinnerGracze;
    JComboBox<Category> comboKategoria;
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
        comboKategoria = new JComboBox<>(Category.values());
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
        // 1. Zabezpieczenie: Sprawdzamy czy imię nie jest puste
        String imieGracza1 = poleImie.getText().trim();
        if (imieGracza1.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Proszę podać imię pierwszego gracza!", "Błąd", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Zabezpieczenie: Sprawdzamy czy wybrano kategorię i tryb (programowanie defensywne)
        if (comboKategoria.getSelectedItem() == null || comboTryb.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Błąd wyboru opcji gry.", "Błąd", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tryb = (String) comboTryb.getSelectedItem();
        Category wybranaKategoriaEnum = (Category) comboKategoria.getSelectedItem();
        String kategoria = wybranaKategoriaEnum.getNazwa();
        
        // JSpinner posiada Model, który zazwyczaj pilnuje zakresu, ale pobranie wartości jest bezpieczne
        int iloscPytan = (int) spinnerPytania.getValue();
        int liczbaGraczy = (int) spinnerGracze.getValue();

        // 3. Walidacja logiczna (już to miałeś - to jest super!)
        if (tryb.equals("Survival") && liczbaGraczy > 1) {
            JOptionPane.showMessageDialog(this, "Tryb Survival jest tylko dla 1 gracza!");
            spinnerGracze.setValue(1); 
            return;
        }

        // 4. Sprawdzanie bazy pytań
        int potrzebnePytania = iloscPytan * liczbaGraczy;
        // Tutaj QuestionLoader.wczytajPytania zwróci pustą listę jeśli plik nie istnieje,
        // co poprawnie obsłuży poniższy warunek.
        ArrayList<Question> dostepne = QuestionLoader.wczytajPytania("pytania.txt", kategoria);
        
        if (dostepne.size() < potrzebnePytania) {
            String komunikat;
            if (dostepne.isEmpty()) {
                komunikat = "Brak pytań w bazie dla kategorii: " + kategoria + ".\nSprawdź plik pytania.txt.";
            } else {
                komunikat = "Nie można rozpocząć gry!\n\n" +
                            "Wybrano: " + liczbaGraczy + " graczy po " + iloscPytan + " pytań.\n" +
                            "Potrzeba łącznie: " + potrzebnePytania + " pytań.\n" +
                            "W bazie dla kategorii '" + kategoria + "' jest tylko: " + dostepne.size() + ".\n\n" +
                            "Zmniejsz liczbę pytań lub wybierz inną kategorię.";
            }
            JOptionPane.showMessageDialog(this, komunikat, "Za mało pytań", JOptionPane.WARNING_MESSAGE);
            return;
        }
   
        // Jeśli przeszliśmy walidację, zbieramy imiona
        ArrayList<String> imionaGraczy = new ArrayList<>();
        imionaGraczy.add(imieGracza1); 

        for (int i = 2; i <= liczbaGraczy; i++) {
            String n = JOptionPane.showInputDialog(this, "Podaj imię dla Gracza " + i + ":");
            // Tutaj też warto zadbać, by nie dodać pustego imienia (zastąpienie domyślnym)
            if (n == null || n.trim().isEmpty()) {
                n = "Gracz " + i;
            }
            imionaGraczy.add(n);
        }

        dispose();

        // Uruchamianie odpowiedniego trybu
        // Switch statement jest czytelniejszy (dostępny od Java 7)
        switch (tryb) {
            case "Survival":
                new Survival(imionaGraczy, tryb, kategoria, iloscPytan);
                break;
            case "Milionerzy":
                new Millionaire(imionaGraczy, tryb, kategoria, iloscPytan);
                break;
            default:
                new Game(imionaGraczy, tryb, kategoria, iloscPytan);
                break;
        }
    }
}